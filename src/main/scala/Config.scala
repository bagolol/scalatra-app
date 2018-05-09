package com.letshout

import com.danielasfregola.twitter4s.TwitterRestClient
import com.letshout.util.TwitterRestClientDummy
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}

object Config {
  private val DEV = "dev"


  lazy val environment = {
    sys.env.getOrElse("ENVIRONMENT", DEV)
  }
  private val consumerKey = System.getenv("TWITTER_CONSUMER_KEY")
  private val consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
  private val accessKey = System.getenv("TWITTER_ACCESS_TOKEN")
  private val accessSecret = System.getenv("TWITTER_TOKEN_SECRET")

  val consumerToken = ConsumerToken(consumerKey, consumerSecret)
  val accessToken = AccessToken(accessKey, accessSecret)

  val LetShoutTwitterRestClient = environment match {
    case "TEST" => TwitterRestClientDummy
    case _ => TwitterRestClient(consumerToken, accessToken)
  }
}