package steps

import steps.ResponseHandler._
import cucumber.api.scala.{EN, ScalaDsl}
import org.json4s.JsonAST._
import org.scalatest.Matchers
import org.scalatest.MustMatchers._
import util.LetShoutApiMockServer._

class BasicApiInfoSteps extends ScalaDsl with EN with Matchers with ApiSteps {
  Before { _ =>
    mockServer.resetRequests()
  }

  Given("""^I navigate to the home page$"""){ () =>
    get(ApiTestServer.letShoutHost)
  }
  Then("""^I am told the name of the application$"""){ () =>
    bodyAsJson mustBe JObject(List(("application",JString("let-shout-api"))))
  }
  Given("""^I navigate to the status$"""){ () =>
    get(ApiTestServer.letShoutHost / "status")
  }
  Then("""^I am shown the application status$"""){ () =>
    bodyAsJson mustBe JObject(List(("status",JString("OK"))))
  }
}

