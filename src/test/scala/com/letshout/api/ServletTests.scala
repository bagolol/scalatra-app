package com.letshout.api

import org.scalatra.test.scalatest._

class ServletTests extends ScalatraFunSuite {

  addServlet(classOf[Servlet], "/*")

  test("GET / on MyScalatraServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
