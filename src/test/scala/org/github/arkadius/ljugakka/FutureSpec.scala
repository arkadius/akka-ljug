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

import scala.concurrent.{Future, Await, Promise}
import scala.concurrent.duration._
import scala.language.postfixOps

class FutureSpec extends FlatSpec with GivenWhenThen with Matchers {
  import concurrent.ExecutionContext.Implicits.global

  "promise" should "notify listeners for future complete" in {
    Given("promise")
    val given = 123
    val promise = Promise[Int]()

    And("thread completing promise after a while")
    new Thread {
      override def run(): Unit = {
        Thread.sleep(1000)
        promise.success(given)
      }
    }.start()

    When("await for future completion")
    val future = promise.future
    val result = Await.result(future, 10 seconds)

    Then("result should be equals with given")
    result shouldEqual given
  }

  "future" should "be tranformable" in {
    val future = Future.successful(1)

    val transformedFuture = future.map(_ + 1)

    val result = Await.result(transformedFuture, 10 seconds)
    result shouldEqual 2
  }

  "futures" should "be composable" in {
    val aFuture = Future {
      Thread.sleep(2000)
      1
    }
    val bFuture = Future {
      Thread.sleep(2000)
      2
    }

    val resultFuture = for {
      a <- aFuture
      b <- bFuture
    } yield a + b

    val result = Await.result(resultFuture, 3 seconds)
    result shouldEqual 3
  }

}