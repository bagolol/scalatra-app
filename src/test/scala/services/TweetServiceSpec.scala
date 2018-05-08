package com.letshout.services

import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.exceptions.{Errors, TwitterError, TwitterException}
import com.letshout.api.RequestParams
import com.letshout.dao.TwitterClient
import org.json4s.{DefaultFormats, Formats}
import org.scalatest.MustMatchers._
import org.json4s._
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, MustMatchers}
import util.TestUtil

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class TweetServiceSpec extends FlatSpec with MockitoSugar with TestUtil with BeforeAndAfter {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  private val mockTwitterClient = mock[TwitterClient]

  object TweetServiceTest extends TweetService {
    override val twitterClient = mockTwitterClient
  }
  val tweetResponse = fixtureAsJson("user_tweets_response.json").extract[Tweet]

  before {
    reset(mockTwitterClient)
  }

  "capitaliseTweets" should "capitalise the text of the tweet" in {
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test", "1")) thenReturn Future.successful(Seq(tweetResponse))

    val result = Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test", "1")
    result.head.text mustBe "THIS IS A GREAT TWEET"
  }

  it should "return an empty list if no tweets were found" in {
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test", "1")) thenReturn Future.successful(Seq())

    val result = Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test", "1")
    result.length mustBe 0
  }

  it should "handle the exception when the TwitterClient throws a generic exception" in {
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test", "1")) thenReturn Future.failed(new Exception("Something went wrong"))

    ScalaFutures.whenReady(TweetServiceTest.capitaliseTweets(params).failed) { error =>
      error mustBe a[Exception]
      error.getMessage mustBe "Something went wrong"
    }
  }

  it should "handle the exception when the TwitterClient throws a TwitterException" in {
    val apiError = new TwitterError("There was an error with the Twitter API", 404)
    val twitterError = new Errors(apiError)
    val twitterException = new TwitterException(404, twitterError)
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test", "1")) thenReturn Future.failed(twitterException)

    ScalaFutures.whenReady(TweetServiceTest.capitaliseTweets(params).failed) { error =>
      error mustBe a[Exception]
      error.getMessage mustBe "[404 Not Found] There was an error with the Twitter API (404)"
    }
  }
}
