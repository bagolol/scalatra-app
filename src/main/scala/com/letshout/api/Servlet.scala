package com.letshout.api

import com.letshout.services.TweetService
import org.json4s.JsonAST.{JObject, JString}
import org.scalatra._

import scala.concurrent.Future
import scala.util.{Failure, Success}
class Servlet extends ScalatraServlet {

  get("/") {
    contentType = "application/json"

    """{"application":"let-shout-api"}"""
  }

  get("/status") {
    contentType = "application/json"

    """{"status":"OK"}"""
  }

  get("/tweets/:user/:quantity") {
    RequestParamsParser(params) match {
      case Success(parsedParams) =>
        new AsyncResult {
          val is: Future[Any] = TweetService.capitaliseTweets(parsedParams)
          }
      case Failure(e) => BadRequest(buildErrorResponse(s"{$e.getMessage}"))
    }
  }

  private def buildErrorResponse(message: String) = JObject("error" -> JString(message))
}
