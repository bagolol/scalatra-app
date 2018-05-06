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

  Given("""^a request for (\d+) tweets from user xyz arrives$"""){ (arg0:Int) =>
    get(ApiTestServer.letShoutHost)
  }
  Then("""^I should receive a success response$"""){ () =>
    statusCode mustBe 200
  }
  Then("""^the response contains the tweets in uppercase$"""){ () =>
    bodyAsJson mustBe JObject(List(("application",JString("let-shout-api"))))
  }

}

