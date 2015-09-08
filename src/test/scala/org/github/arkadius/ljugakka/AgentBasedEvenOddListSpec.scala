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

import org.scalatest.{GivenWhenThen, FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class AgentBasedEvenOddListSpec extends FlatSpec with Matchers with GivenWhenThen {
  import Utils._
  import concurrent.ExecutionContext.Implicits.global

  "agent based list" should "alternately add even and odd asynchronous" in {
    Given("actor based list")
    val list = new AgentBasedEvenOddList()

    When("add n times asynchronous")
    val n = 10
    n times {
      list.addEvenOrOdd()
    }

    Then("list should print alternately true and false")
    val futureResult = list.printToString()
    val result = Await.result(futureResult, 10 seconds)

    result shouldEqual (n / 2).times("01").mkString("")
  }

}