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

import akka.actor.FSM

class FSMBasedStateMachine extends FSM[State, Int] {

  startWith(WaitForNextEven, 0)

  when(WaitForNextEven) {
    case Event(NextEven, current) =>
      sender() ! current
      goto(WaitForNextOdd) using current+1
  }

  when(WaitForNextOdd) {
    case Event(NextOdd, current) =>
      sender() ! current
      goto(WaitForNextEven) using current+1
  }

}

sealed trait State
case object WaitForNextEven extends State
case object WaitForNextOdd extends State