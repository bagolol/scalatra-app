package util

import com.github.tomakehurst.wiremock.WireMockServer

object LetShoutApiMockServer {
  val port = 8080
  println(s">>> Starting Mock Server for testing on port [$port]")
  val stubbedServerUrl = s"http://localhost:$port"
  val mockServer = new WireMockServer(port)
}
