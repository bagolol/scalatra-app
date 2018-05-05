package steps

import java.util.concurrent.Executors

import com.letshout.api.Servlet
import com.ning.http.client.Response
import cucumber.api.scala.{EN, ScalaDsl}
import dispatch.{Http, Req, host}
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
    ResponseHandler.statusCode = completeResponse.getStatus
    ResponseHandler.bodyAsString = completeResponse.getBodyAsString

    Try {
      parse(completeResponse.getBodyAsString)
    } match {
      case Success(jsonResponse) => ResponseHandler.bodyAsJson = jsonResponse
      case Failure(_) =>
    }
  }

  def get(request: Req) = {
    val req = Http.default(request.setHeader("Accept", "application/json").setMethod("GET"))
    setResponse(req)
  }

  def get(request: Req, params: Map[String, Seq[String]]) = {
    val req = Http.default(request.setHeader("Accept", "application/json").setMethod("GET").setQueryParameters(params))
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

  val optimoAssetStoreHost = host("localhost", port)

  implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global

  Servlets().foreach(servletMapping => {
    val servlet = servletMapping._1
    val endpoint = servletMapping._2
    val servletHolder = new ServletHolder(servlet)
    context.addServlet(servletHolder, endpoint)
  })
  context.setResourceBase("src/main/webapp")
  context.addServlet(new ServletHolder("default", classOf[DefaultServlet]), "/")
  server.start()

  implicit val system = ServiceActorSystem.actorSystem
  system.scheduler.schedule(initialDelay = 0.seconds, interval = 2000.milliseconds)({
    TransitionService.processMessages()
  })
}

object ServiceActorSystem {
  val actorSystem = ActorSystem("test-optimo-asset-store")
}
