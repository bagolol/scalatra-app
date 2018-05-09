package com.letshout.util

import com.letshout.Config._
import com.danielasfregola.twitter4s._
import com.danielasfregola.twitter4s.entities.{RateLimit, RatedData, Tweet}
import java.util.Date

import com.danielasfregola.twitter4s.entities.enums.TweetMode.TweetMode

import scala.concurrent.Future



object TwitterRestClientDummy extends TwitterRestClient(accessToken = accessToken, consumerToken = consumerToken) {
  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global

  private val rateLimit = new RateLimit(limit = 10, remaining = 10, reset = new Date())
  private val tweet = Tweet(created_at = new Date(), id = 12345678910L, id_str = "test", source = "test", text = "Test Text")

  override def userTimelineForUser(screen_name: String, since_id: Option[Long], count: Int, max_id: Option[Long], trim_user: Boolean, exclude_replies: Boolean, contributor_details: Boolean, include_rts: Boolean, tweet_mode: TweetMode): Future[RatedData[Seq[Tweet]]] = {
    screen_name match {
      case "nonExistent" => Future.failed(new Exception("The user doesn't exist"))
      case "userWithNoTweets" => {
        val ratedData = new RatedData(rateLimit, Seq(tweet))
        Future(ratedData)
      }
      case _ => {
        val ratedData = new RatedData(rateLimit, multiplyTweets(count, tweet))
        Future(ratedData)
      }
    }
  }

  private def multiplyTweets(count: Int, tweet: Tweet): Seq[Tweet] = {
    List.fill(count)(tweet)
  }

}