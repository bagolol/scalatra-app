package com.letshout.dao

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{Tweet}
import com.letshout.Config._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TwitterClient {
  private val client = TwitterRestClient(consumerToken, accessToken)

  def searchTweetsForUser(username: String): Future[Seq[Tweet]] = {
    client.userTimelineForUser(username).map { ratedData =>
      ratedData.data
    }
  }
}

object TwitterClient extends TwitterClient