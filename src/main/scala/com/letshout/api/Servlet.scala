package com.letshout.api

import akka.actor.ActorSystem
import org.json4s.{DefaultFormats, Formats}
import com.letshout.services.TweetService
import org.json4s.JsonAST.{JObject, JString}
import org.scalatra._
import org.json4s.jackson.Serialization.write
import org.json4s._
import org.json4s.ext.JavaTypesSerializers
import org.json4s.native.Serialization

import scala.util.{Failure, Success}
import org.scalatra.json._

import scala.concurrent.ExecutionContext


class Servlet(system: ActorSystem) extends ScalatraServlet with JacksonJsonSupport with FutureSupport {

//  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global
  protected implicit def executor: ExecutionContext = system.dispatcher
//  protected implicit val jsonFormats: Formats = DefaultFormats
  protected implicit lazy val jsonFormats: Formats = Serialization.formats(NoTypeHints) ++ JavaTypesSerializers.all

  before() {
    contentType = formats("json")
  }

  get("/") {

    """{"application":"let-shout-api"}"""
  }

  get("/status") {

    """{"status":"OK"}"""
  }

  get("/tweets/:username/:limit") {
    RequestParamsParser(params) match {
      case Success(parsedParams) =>
        new AsyncResult {
          val is = TweetService.capitaliseTweets(parsedParams) map { tweets =>
            println("SUCCESS")
            Ok(write(tweets))
          }
        }
      case Failure(e) => BadRequest(buildErrorResponse(s"{$e.getMessage}"))
    }
  }

  private def buildErrorResponse(message: String) = JObject("error" -> JString(message))
}
case class Person(name: String,
                  text: String)
