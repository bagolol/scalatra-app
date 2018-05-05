package steps

import java.util.concurrent.Executors
import com.letshout.api.Servlet
import scalaj.http._
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


  def get(url: String) = {
    Http(url).header("Accept", "application/json").asString
  }

//  def get(request: Req, params: Map[String, Seq[String]]) = {
//    val req = Http(request.setHeader("Accept", "application/json").setMethod("GET").setQueryParameters(params))
//    setResponse(req)
//  }

  ApiTestServer
}


object ApiTestServer {
  private val port = 8080
  private val server = new Server(port)
  private val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)

  val servlet = new Servlet;
  val servletHolder = new ServletHolder(servlet)
  context.addServlet(servletHolder, "/*")

  implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global

  context.setResourceBase("src/main/webapp")
  context.addServlet(new ServletHolder("default", classOf[DefaultServlet]), "/")
  server.start()

}

