package steps

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import com.letshout.api.Servlet
import org.asynchttpclient.Response
import dispatch._
import cucumber.api.scala.{EN, ScalaDsl}
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler, ServletHolder}
import org.json4s.JsonAST.JValue
import org.json4s.native.JsonMethods.parse

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

trait ApiSteps extends ScalaDsl with EN {
  implicit val executionContext = ExecutionContext fromExecutorService Executors.newSingleThreadExecutor

  private def setResponse(req: Future[Response]) = {
    val completeResponse = Await.result(req, 10.seconds)
    ResponseHandler.response = completeResponse
    ResponseHandler.statusCode = completeResponse.getStatusCode
    ResponseHandler.bodyAsString = completeResponse.getResponseBody

    Try {
      parse(completeResponse.getResponseBody)
    } match {
      case Success(jsonResponse) => ResponseHandler.bodyAsJson = jsonResponse
      case Failure(_) =>
    }
  }

  def get(request: Req) = {
    val req = Http.default(request.setHeader("Accept", "application/json").setMethod("GET"))
    setResponse(req)
  }

  ApiTestServer
}

object ResponseHandler {
  var response: Response = _
  var statusCode: Int = _
  var bodyAsJson: JValue = _
  var bodyAsString: String = _
}


object ApiTestServer {
  private val port = 8080
  private val server = new Server(port)
  private val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)

  val letShoutHost = host("localhost", port)
  val servlet = new Servlet()
  val servletHolder = new ServletHolder(servlet)
  context.addServlet(servletHolder, "/*")

  implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global

  context.setResourceBase("src/main/webapp")
  context.addServlet(new ServletHolder("default", classOf[DefaultServlet]), "/")
  server.start()
}

