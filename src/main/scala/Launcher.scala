import akka.actor.ActorSystem
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener


object Launcher extends App {
  implicit val system = ServiceActorSystem.actorSystem
  implicit val ec =  scala.concurrent.ExecutionContext.Implicits.global

  val port = sys.props.get("jetty.port") map (_.toInt) getOrElse 8080

  val server = new Server(port)
  val context = new WebAppContext


  context.setContextPath("/")

  val resourceBase = getClass.getClassLoader.getResource("webapp").toExternalForm
  context.setResourceBase(resourceBase)

  context.addEventListener(new ScalatraListener)
  context.addServlet(classOf[DefaultServlet], "/")

  server.setHandler(context)
  server.start()
  server.join()


}

object ServiceActorSystem {
  val actorSystem = ActorSystem("letshout-app")
}
