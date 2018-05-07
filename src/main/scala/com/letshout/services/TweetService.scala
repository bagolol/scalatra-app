package com.letshout.services

import com.danielasfregola.twitter4s.entities.Tweet
import com.letshout.api.RequestParams
import com.letshout.dao.TwitterClient


import scala.concurrent.Future

trait TweetService {

  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global
  val twitterClient: TwitterClient

  def capitaliseTweets(params: RequestParams): Future[Seq[Tweet]] = {
      for {
        tweets <- TwitterClient.searchTweetsForUser(params.username)
        requestedTweets = tweets.take(params.limit.toInt)
        capitalisedTweets = capitaliseText(requestedTweets)
      } yield capitalisedTweets
  }

  private def capitaliseText(tweets: Seq[Tweet]): Seq[Tweet] = {
    tweets.map(tweet => tweet.copy(text = tweet.text.toUpperCase))
  }
}

object TweetService extends TweetService {
  val twitterClient = TwitterClient
}