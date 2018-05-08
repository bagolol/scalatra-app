package steps

import steps.ResponseHandler._
import cucumber.api.scala.{EN, ScalaDsl}
import org.json4s.JsonAST._
import org.scalatest.Matchers
import org.scalatest.MustMatchers._
import com.letshout.util.LetShoutApiMockServer._

class GettingTweetsSteps extends ScalaDsl with EN with Matchers with ApiSteps {
  Before { _ =>
    mockServer.resetRequests()
  }

  Given("""^a request for 2 tweets from user xyz arrives$""") { () =>
    get(ApiTestServer.letShoutHost / "tweets" / "xyz" / "2")
  }
  Then("""^I should receive a success response$""") { () =>
    statusCode mustBe 200
  }
  And("""^the response contains 2 tweets$""") { () =>
    bodyAsJson.asInstanceOf[JArray].values.size mustBe 2
  }

  And("""^the text is uppercase$""") { () =>
    (bodyAsJson(0) \ "text") mustBe JString("TEST TEXT")
    (bodyAsJson(1) \ "text") mustBe JString("TEST TEXT")
  }
}
