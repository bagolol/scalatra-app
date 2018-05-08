package com.letshout.api

import akka.actor.ActorSystem
import org.json4s.{DefaultFormats, Formats}
import com.letshout.services.TweetService
import org.json4s.JsonAST.{JObject, JString}
import org.scalatra._
import org.json4s.jackson.Serialization.write

import scala.util.{Failure, Success}
import org.scalatra.json._

import scala.concurrent.ExecutionContext


class Servlet extends ScalatraServlet with JacksonJsonSupport with FutureSupport {

  protected implicit def executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/") {

    Ok("""{"application":"let-shout-api"}""")
  }

  get("/status") {

    """{"status":"OK"}"""
  }

  get("/tweets/:username/:limit") {
    RequestParamsParser(params) match {
      case Success(parsedParams) =>
        new AsyncResult {
          val is = TweetService.capitaliseTweets(parsedParams) map { tweets =>
            Ok(write(tweets))
          }
        }
      case Failure(e) => BadRequest(buildErrorResponse(s"{$e.getMessage}"))
    }
  }

  private def buildErrorResponse(message: String) = JObject("error" -> JString(message))
}
