package com.letshout.api

import org.scalatra._

class Servlet extends ScalatraServlet {

  get("/") {
    contentType = "application/json"

    """{"application":"let-shout-api"}"""
  }

  get("/status") {
    contentType = "application/json"

    """{"status":"OK"}"""
  }

}
