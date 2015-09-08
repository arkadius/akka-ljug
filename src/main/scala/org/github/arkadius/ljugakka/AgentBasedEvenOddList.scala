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

import akka.agent.Agent

import scala.concurrent.{Future, ExecutionContext}
import scala.util.Random

class AgentBasedEvenOddList(implicit ec: ExecutionContext) {

  private val random = new Random
  private val agent = Agent(List[Boolean]())

  def addEvenOrOdd(): Future[List[Boolean]] = {
    agent.alter { list =>
      val willBeEven = list.size % 2 == 0
      Thread.sleep(random.nextInt(100))
      willBeEven :: list 
    }
  }

  def printToString(): Future[String] = {
    agent.future().map { list =>
      list.reverse.map {
        case true => "0"
        case false => "1"
      }.mkString("")
    }
  }
  
}