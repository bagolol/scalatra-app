package com.letshout.dao

import com.danielasfregola.twitter4s.entities.{Tweet}
import com.danielasfregola.twitter4s.exceptions.{TwitterException}
import com.letshout.Config.LetShoutTwitterRestClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TwitterClient {

  def searchTweetsForUser(username: String, count: String): Future[Seq[Tweet]] = {
    println("===========================")
    LetShoutTwitterRestClient.userTimelineForUser(username, count = count.toInt)
      .map { ratedData =>
        ratedData.data
      } recoverWith {
      case te: TwitterException =>
        Future.failed(new Exception("There was an error with the Twitter API", te))
      case t: Throwable =>
        Future.failed(new Exception("Something went wrong", t))
    }
  }
}

object TwitterClient extends TwitterClient