package com.letshout.services

import org.scalatest.{BeforeAndAfter, FlatSpec}
import org.scalatest.mockito.MockitoSugar
import org.joda.time.format.ISODateTimeFormat
import org.json4s.JsonAST.{JObject, JString}
import org.json4s.native.Serialization._
import org.json4s.{DefaultFormats, Formats}
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.MustMatchers._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec}

import scala.util.{Failure, Success}


class TweetServiceSpec extends FlatSpec with MockitoSugar with BeforeAndAfter {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats


  object TweetServiceTest extends TweetService {
  }

  "capitaliseTweets" should "invoke the twitter client" in {
    val expected = List(JObject())
    val result = TweetServiceTest.capitaliseTweets("username", "2")

    result mustBe expected
  }



}
