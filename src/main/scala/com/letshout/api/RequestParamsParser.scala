package com.letshout.api

import org.scalatra.Params

import scala.util.{Failure, Success, Try}


object RequestParamsParser {

  private val ERR_MSG_LIMIT_TOO_BIG = "Invalid limit parameter: max number of tweets is 10"
  private val ERR_MSG_LIMIT_INV = "Invalid limit parameter: not an integer"
  private val ERR_MSG_LIMIT_NOT_POS = "Invalid limit parameter: min number of tweets is 1"

  def apply(params: Params): Try[RequestParams] = {
    val userParam = params("username")
    val limitParam = params("limit")

    for {
      validLimit <- parseLimit(limitParam)
    } yield {
      RequestParams(userParam, limitParam)
    }
  }

  private def parseLimit(limit: String): Try[Int] =
  Try {
    limit.toInt
  }
    match {
    case Success(limit) if limit > 10 => Failure(new InvalidRequestException(ERR_MSG_LIMIT_TOO_BIG))
    case Success(limit) if limit < 1 => Failure(new InvalidRequestException(ERR_MSG_LIMIT_NOT_POS))
    case Success(limit) => Success(limit)
  }


}

class InvalidRequestException(message: String) extends Exception(message)

case class RequestParams(username: String,
                         limit: String)

