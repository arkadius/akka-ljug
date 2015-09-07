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

import akka.actor._
import akka.testkit._
import org.scalatest._

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class SampleActorSpec extends TestKit(ActorSystem("ljug")) with FlatSpecLike with GivenWhenThen with ImplicitSender {
  import Utils._
  import system.dispatcher

  "actor" should "alternately add even and odd for multiple threads" in {
    Given("actor")
    val actor = system.actorOf(Props(classOf[SampleActor]))

    When("was sent command to add even or odd in few threads")
    val n = 10
    val futures = n times {
      Future {
        actor ! AddEvenOrOdd
      }
    }
    val sequenced = Future.sequence(futures)
    Await.result(sequenced, 10 seconds)

    And("then was send ToString commmand")
    actor ! ToString

    Then("actor should reply with alternately true and false")
    expectMsg((n / 2).times("01").mkString(""))

    ()
  }

}