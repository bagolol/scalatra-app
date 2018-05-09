Feature: Getting Tweets

    Scenario: Retrieve tweets from Twitter
      Given a request for 2 tweets from user "xyz" arrives
      Then I should receive a success response
      And the response contains 2 tweets
      And the text is uppercase

    Scenario: A user with no tweets
      Given a request for 2 tweets from user "userWithNoTweets" arrives
      Then I should see a 404 response code
      And the response contains no tweets

    Scenario: A non existing user
      Given a request for 2 tweets from user "nonExistent" arrives
      Then I should see a 400 response code

    Scenario: An invalid number
      Given a request for an invalid number of tweet arrives
      Then I should see a 400 response code



