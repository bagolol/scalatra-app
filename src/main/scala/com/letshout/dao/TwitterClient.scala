package com.letshout.dao

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, RatedData, Tweet}
import com.danielasfregola.twitter4s.entities.enums.ResultType
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TwitterClient extends App {


  //TODO move this into a config File

  private val consumerKey = System.getenv("TWITTER_CONSUMER_KEY")
  private val consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
  private val accessKey = System.getenv("TWITTER_ACCESS_TOKEN")
  private val accessSecret = System.getenv("TWITTER_TOKEN_SECRET")

  private val consumerToken = ConsumerToken(consumerKey, consumerSecret)
  private val accessToken = AccessToken(accessKey, accessSecret)

  val client = TwitterRestClient(consumerToken, accessToken)

  def searchTweetsForUser(username: String): Future[Seq[Tweet]] = {
    client.userTimelineForUser(username).map { ratedData =>
      ratedData.data
    }
  }

}

object TwitterClient extends TwitterClient