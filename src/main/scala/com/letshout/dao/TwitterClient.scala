package com.letshout.dao

import com.danielasfregola.twitter4s.entities.{Tweet}
import com.letshout.Config.LetShoutTwitterRestClient
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TwitterClient {
  def searchTweetsForUser(username: String): Future[Seq[Tweet]] = {
    LetShoutTwitterRestClient.userTimelineForUser(username).map { ratedData =>
      ratedData.data
    }
  }
}

object TwitterClient extends TwitterClient