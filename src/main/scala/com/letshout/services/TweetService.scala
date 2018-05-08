package com.letshout.services

import com.danielasfregola.twitter4s.entities.Tweet
import com.danielasfregola.twitter4s.exceptions.TwitterException
import com.letshout.api.RequestParams
import com.letshout.dao.TwitterClient
import com.sun.jmx.snmp.SnmpV3Message

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait TweetService {

  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global
  val twitterClient: TwitterClient

  def capitaliseTweets(params: RequestParams): Future[Seq[Tweet]] = {
    (for {
      tweets <- twitterClient.searchTweetsForUser(params.username)
      requestedTweets = tweets.take(params.limit.toInt)
      capitalisedTweets = capitaliseText(requestedTweets)
    } yield capitalisedTweets) recoverWith {
      case t: Throwable =>
        Future.failed(new Exception(t.getMessage))
    }
  }

  private def capitaliseText(tweets: Seq[Tweet]): Seq[Tweet] = {
    tweets.map(tweet => tweet.copy(text = tweet.text.toUpperCase))
  }
}

object TweetService extends TweetService {
  val twitterClient = TwitterClient
}