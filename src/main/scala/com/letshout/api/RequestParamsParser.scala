package com.letshout.api

import bbc.cps.optimoassetstore.exceptions.InvalidRequestException
import org.joda.time.DateTime
import org.joda.time.chrono.ISOChronology
import org.scalatra.Params

import scala.util.{Failure, Success, Try}


object RequestParamsParser {

  private val ERR_MSG_LIMIT_INV = "Invalid limit parameter: not an integer"
  private val ERR_MSG_LIMIT_NOT_POS = "Invalid limit parameter: must be a positive integer"

  def apply(params: Params): Try[RequestParams] = {
    val userParam = params.get("username") map (_.toLowerCase)
    val limitParam = params.get("limit")

    for {
      validLimit <- parseLimit(limitParam)
    } yield {
      RequestParams(userParam, limitParam)
    }
  }

  private def parseLimit(limitOpt: Option[String]): Try[Option[Int]] =
    Try {
      limitOpt map {
        _.toInt
      }
    } match {
      case Success(Some(limit)) if limit > Config.maxPageSize => Failure(new InvalidRequestException(ERR_MSG_LIMIT_TOO_BIG))
      case Success(Some(limit)) if limit < 1 => Failure(new InvalidRequestException(ERR_MSG_LIMIT_NOT_POS))
      case Success(limit) => Success(limit)
      case Failure(e) => Failure(new InvalidRequestException(ERR_MSG_LIMIT_INV))
    }

  private def parseDate(dateOpt: Option[String], paramName: String): Try[Option[DateTime]] =
    Try {
      dateOpt map {
        DateTime.parse(_)
      }
    } match {
      case Success(date) => Success(date)
      case Failure(e) => Failure(new InvalidRequestException(ERR_MSG_DATE_INV.replace("%s", paramName)))
    }

  private def checkDates(beforeOpt: Option[DateTime], sinceOpt: Option[DateTime]): Try[Unit] =
    (beforeOpt, sinceOpt) match {
      case (Some(before), Some(since)) if since.isAfter(before) => Failure(new InvalidRequestException(ERR_MSG_INV_WIN))
      case (_, _) => Success(())
    }

  private def checkOrdering(ordering: Option[String]): Try[Option[Ordering.Value]] =
    ordering match {
      case Some(str) =>
        Ordering.withNameOpt(str) match {
          case None => Failure(new InvalidRequestException(ERR_MSG_INV_ORDERING))
          case order => Success(order)
        }
      case None => Success(None)
    }

}
case class RequestParams(username: String,
                         limit: Int)

