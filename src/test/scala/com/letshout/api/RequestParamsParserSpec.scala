package com.letshout.api

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, MustMatchers}
import org.scalatra.ScalatraParams

import scala.util.Success


class RequestParamsParserSpec extends FlatSpec with MockitoSugar with MustMatchers with BeforeAndAfter {

  private def scalatraParams(username: String, limit: String) = {
    val a = Map("username" -> Seq(username))
    val l = Map("limit" -> Seq(limit))
    val m = Map() ++ a ++ l
    new ScalatraParams(m)
  }


  "RequestParamsParser" should "return request params if limit is greater than 0" in {
    val params = scalatraParams("abcd", "1")
    val actual = RequestParamsParser(params)
    val expected = Success(RequestParams("abcd", "1"))
    actual mustBe expected
  }


  it should "return failure when limit is greater that the max allowed number of tweets" in {
    val params = scalatraParams("abcd", "15")
    val actual = RequestParamsParser(params)
    actual.failed.get.getMessage mustBe s"Invalid limit parameter: max number of tweets is 10"
  }

  it should "return failure when limit is set to 0" in {
    val params = scalatraParams("abcd", "0")
    val actual = RequestParamsParser(params)
    actual.failed.get.getMessage mustBe s"Invalid limit parameter: min number of tweets is 1"
  }

}
