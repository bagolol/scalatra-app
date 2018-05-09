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

  Given("""^a request for (\d+) tweets from user "([^"]*)" arrives$""") { (count: Int, user: String) =>
    get(ApiTestServer.letShoutHost / "tweets" / user / "2")
  }

  Given("""^a request for an invalid number of tweet arrives$"""){ () =>
    get(ApiTestServer.letShoutHost / "tweets" / "xyz" / "invalid")
  }

  Then("""^I should receive a success response$""") { () =>
    statusCode mustBe 200
  }
  And("""^the response contains (\d+) tweets$""") { (count: Int) =>
    bodyAsJson.asInstanceOf[JArray].values.size mustBe count
  }

  And("""^the response contains no tweets$""") { () =>
    bodyAsJson \ "error" mustBe JString("There are no tweets for request userWithNoTweets")
  }

  And("""^the text is uppercase$""") { () =>
    (bodyAsJson(0) \ "text") mustBe JString("TEST TEXT")
    (bodyAsJson(1) \ "text") mustBe JString("TEST TEXT")
  }
  Then("""^I should see a (\d+) response code$""") { (code: Int) =>
    statusCode mustBe code
  }
}

