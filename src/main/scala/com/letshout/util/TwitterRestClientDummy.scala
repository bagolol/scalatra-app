package com.letshout.util

import com.letshout.Config._
import com.danielasfregola.twitter4s._
import com.danielasfregola.twitter4s.entities.{RateLimit, RatedData, Tweet}
import java.util.Date

import scala.concurrent.Future



object TwitterRestClientDummy extends TwitterRestClient(accessToken = accessToken, consumerToken = consumerToken) {
  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global

//  val tweet = Tweet(created_at = new Date(), id = new Long(), id_str = "test", source = "test", text = "Test Text")
//  val ratedData: RatedData[Seq[Tweet]] = new RatedData(new RateLimit(limit = 10, remaining = 10, reset = new Date()), data = Seq(tweet))
////  override def userTimelineForUser(screen_name: String): Future[RatedData[Seq[Tweet]]] = {
////      Future(ratedData)
////  }
//
//  override def userTimelineForUser(screen_name: _root_.scala.Predef.String, since_id: _root_.scala.Option[Long], count: Int, max_id: _root_.scala.Option[Long], trim_user: Boolean, exclude_replies: Boolean, contributor_details: Boolean, include_rts: Boolean, tweet_mode: _root_.com.danielasfregola.twitter4s.entities.enums.TweetMode.TweetMode): Future[RatedData[Seq[Tweet]]] = {
//    Future(ratedData)
//  }


}