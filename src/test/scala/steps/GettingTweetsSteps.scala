package steps

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import org.scalatest.MustMatchers._

class GettingTweetsSteps extends ScalaDsl with EN with Matchers {

  Given("""^a request for (\d+) tweets from user xyz arrives$"""){ (arg0:Int) =>
    //// Write code here that turns the phrase above into concrete actions
    true mustBe true
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