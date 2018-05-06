Feature: Getting Tweets

    Scenario: Retrieve tweets from Twitter
      Given a request for 2 tweets from user xyz arrives
      Then I should receive a success response
      And the response contains 2 tweets
      And the text is uppercase

