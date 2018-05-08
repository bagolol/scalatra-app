package com.letshout.util

import com.letshout.Config._
import com.danielasfregola.twitter4s._
import com.danielasfregola.twitter4s.entities.{RatedData, Tweet}
import scala.concurrent.Future



object TwitterClientDummy extends TwitterRestClient(accessToken = accessToken, consumerToken = consumerToken) {
  override def userTimelineForUser(screen_name: String): Future[RatedData[Seq[Tweet]]] = {
  }


}