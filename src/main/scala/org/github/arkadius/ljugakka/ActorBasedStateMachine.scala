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

import akka.actor.Actor

class ActorBasedStateMachine extends Actor {

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    context.become(waitForNextEven(0))
  }

  override def receive: Receive = PartialFunction.empty

  def waitForNextEven(current: Int): Receive = {
    case NextEven =>
      sender() ! current
      context.become(waitForNextOdd(current + 1), discardOld = true)
  }
  
  def waitForNextOdd(current: Int): Receive = {
    case NextOdd =>
      sender() ! current
      context.become(waitForNextEven(current + 1), discardOld = true)
  }
  
}

case object NextEven
case object NextOdd