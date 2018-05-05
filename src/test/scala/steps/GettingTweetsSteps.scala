package steps

import scalaj.http._
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import org.scalatest.MustMatchers._
import util.LetShoutApiMockServer

class GettingTweetsSteps extends ScalaDsl with EN with Matchers with ApiSteps {

  Given("""^a request for (\d+) tweets from user xyz arrives$"""){ (arg0:Int) =>
    val response = Http(LetShoutApiMockServer.stubbedServerUrl)
      .header("Accept", "application/json")
      .asString

    response.code mustBe 200
  }
  Then("""^I should receive a success response$"""){ () =>
    //// Write code here that turns the phrase above into concrete actions
    true mustBe true
  }
  Then("""^the response contains the tweets in uppercase$"""){ () =>
    //// Write code here that turns the phrase above into concrete actions
    true mustBe true
  }

}