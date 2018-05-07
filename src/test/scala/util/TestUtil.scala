package util

import org.json4s._
import org.json4s.native.JsonMethods._

import scala.concurrent.duration._
import scala.concurrent.{Await, Awaitable}
import scala.io.Source

trait TestUtil {

  def fixtureAsString(path: String): String = {
    Source.fromFile(s"src/test/resources/fixtures/$path").mkString
  }

  def fixtureAsJson(path: String): JValue = {
    parse {
      Source.fromFile(s"src/test/resources/fixtures/$path").mkString
    }
  }


  def complete[T](awaitable: => Awaitable[T]): T = Await.result(awaitable, 5.seconds)
}