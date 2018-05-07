package com.letshout.api

import com.danielasfregola.twitter4s.entities.Tweet
import com.letshout.services.TweetService
import org.json4s.JsonAST.{JObject, JString}
import org.scalatra._

import scala.concurrent.Future
import scala.util.{Failure, Success}
class Servlet extends ScalatraServlet {

  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global

  get("/") {
    contentType = "application/json"

    """{"application":"let-shout-api"}"""
  }

  get("/status") {
    contentType = "application/json"

    """{"status":"OK"}"""
  }

  get("/tweets/:username/:limit") {
    contentType = "application/json"
    RequestParamsParser(params) match {
      case Success(parsedParams) =>
        new AsyncResult {
          val is = TweetService.capitaliseTweets(parsedParams) map { tweets =>
            println(tweets)
            println("========================================")
            Ok(tweets)
          }
        }
      case Failure(e) => BadRequest(buildErrorResponse(s"{$e.getMessage}"))
    }
  }

  private def buildErrorResponse(message: String) = JObject("error" -> JString(message))
}
