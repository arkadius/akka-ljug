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

import scala.util.Random

class SampleActor extends Actor {

  private val random = new Random
  private val list = collection.mutable.ArrayBuffer[Boolean]()
  
  override def receive: Receive = {
    case AddEvenOrOdd =>
      val isEven = list.size % 2 == 0
      Thread.sleep(random.nextInt(100))
      if (isEven)
        list += true
      else
        list += false
    case ToString =>
      sender() ! list.map {
        case true => "0"
        case false => "1"
      }.mkString("")      
  }
  
  
}

case object AddEvenOrOdd
case object ToString