/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.github.arkadius.ljugakka

import akka.actor.{PoisonPill, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.FlatSpecLike
import org.scalatest.prop.PropertyChecks

class StateMachineSpec extends TestKit(ActorSystem("StateMachineSpec")) with ImplicitSender with FlatSpecLike with PropertyChecks {

  private val actorClasses = Table("actor class", classOf[ActorBasedStateMachine], classOf[FSMBasedStateMachine])

  "state machine" should "alternately reply with even and odd" in {
    forAll(actorClasses) { actorClass =>
      val actor = system.actorOf(Props(actorClass))

      actor ! NextEven
      expectMsg(0)

      actor ! NextEven
      expectNoMsg()

      actor ! NextOdd
      expectMsg(1)

      actor ! NextEven
      expectMsg(2)

      actor ! PoisonPill
    }
  }
}