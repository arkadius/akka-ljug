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

import org.scalatest.{GivenWhenThen, Matchers, FlatSpec}

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class SampleMonitorSpec extends FlatSpec with GivenWhenThen with Matchers {
  import concurrent.ExecutionContext.Implicits.global
  import Utils._

  "monitor" should "alternately add even and odd for multiple threads" in {
    Given("monitor")
    val monitor = new SampleMonitor

    When("was added even or odd in few threads")
    val count = 10
    val futures = count times {
      Future {
        monitor.addEvenOrOdd()
      }
    }
    val sequenced = Future.sequence(futures)
    Await.result(sequenced, 10 seconds)

    Then("monitor should contains alternately true and false")
    monitor.toString shouldEqual (count / 2).times("01").mkString("")
  }

}