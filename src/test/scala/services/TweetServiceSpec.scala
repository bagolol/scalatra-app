package com.letshout.services

import com.danielasfregola.twitter4s.entities.Tweet
import com.letshout.api.RequestParams
import com.letshout.dao.TwitterClient
import org.json4s.{DefaultFormats, Formats}
import org.scalatest.MustMatchers._
import org.json4s._
import org.mockito.Mockito._
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

  before {
    reset(mockTwitterClient)
  }
  "capitaliseTweets" should "invoke the twitter client" in {
    val tweetResponse = fixtureAsJson("user_tweets_response.json").extract[Tweet]
    val params = new RequestParams("test", "10")
    when(mockTwitterClient.searchTweetsForUser("test")) thenReturn Future.successful(Seq(tweetResponse))
    Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test")
  }

  it should "return the requested amount of tweets" in {
    val tweetResponse = fixtureAsJson("user_tweets_response.json").extract[Tweet]

    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test")) thenReturn Future.successful(Seq(tweetResponse, tweetResponse))

    val result = Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test")
    result.length mustBe 1
  }

  it should "return an empty list if no tweets were found" in {
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test")) thenReturn Future.successful(Seq())

    val result = Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test")
    result.length mustBe 0
  }

  it should "handle the exception when the user does not exist" in {
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test")) thenReturn Future.successful(Seq())

    val result = Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test")
    result.length mustBe 0
  }

  it should "capitalise the text of the tweet" in {
    val params = new RequestParams("test", "1")
    when(mockTwitterClient.searchTweetsForUser("test")) thenReturn Future.successful(Seq())

    val result = Await.result(TweetServiceTest.capitaliseTweets(params), 5.seconds)

    verify(mockTwitterClient).searchTweetsForUser("test")
    result.length mustBe 0
  }


}
