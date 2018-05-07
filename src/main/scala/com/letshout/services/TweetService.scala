package com.letshout.services

import com.danielasfregola.twitter4s.entities.Tweet
import com.letshout.api.RequestParams
import com.letshout.dao.TwitterClient


import scala.concurrent.Future

trait TweetService {
  val twitterClient: TwitterClient

  def capitaliseTweets(params: RequestParams): Future[List[Tweet]] = {
      for {
        tweets <- TwitterClient.searchTweetsForUser(params.username)
        requestedTweets = tweets.take(params.limit.toInt)
        capitalizedTweets <- capitalizeText(requestedTweets)
      } yield capitalizedTweets
  }

  private def capitalizeText(tweets: Seq[Tweet]): Seq[Tweet] = {
    tweets.map { tweet =>
      tweet.copy(text = tweet.text.toUpperCase)
    }
  }
}

object TweetService extends TweetService {
  val twitterClient = TwitterClient
}