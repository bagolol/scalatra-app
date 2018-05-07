import _root_.akka.actor.{Props, ActorSystem}
import com.letshout.api._
import org.scalatra._
import javax.servlet.ServletContext


class ScalatraBootstrap extends LifeCycle {

  val system = ActorSystem()

  override def init(context: ServletContext) {
    context.mount(new Servlet(system), "/*")
  }
}
