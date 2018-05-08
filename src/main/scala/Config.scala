package com.letshout

import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken}
import org.json4s.JsonAST.JString

object Config {
  private val DEV = "dev"


  lazy val environment = {
    sys.env.getOrElse("ENVIRONMENT", DEV)
  }

  val TwitterClient = environment match {
    case "TEST" =>


  }
  private val consumerKey = System.getenv("TWITTER_CONSUMER_KEY")
  private val consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET")
  private val accessKey = System.getenv("TWITTER_ACCESS_TOKEN")
  private val accessSecret = System.getenv("TWITTER_TOKEN_SECRET")

  val consumerToken = ConsumerToken(consumerKey, consumerSecret)
  val accessToken = AccessToken(accessKey, accessSecret)

}