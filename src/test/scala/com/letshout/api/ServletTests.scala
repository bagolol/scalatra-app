package com.letshout.api

import org.scalatra.test.scalatest._

class ServletTests extends ScalatraFunSuite {

  addServlet(classOf[Servlet], "/*")

  test("GET / on Servlet should return status 200") {
  }

}
