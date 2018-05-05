Feature: Getting Tweets

    Scenario: Retrieve tweets from Twitter
        Given a request for 4 tweets from user xyz arrives
        Then I should receive a success response
        And the response contains the tweets in uppercase
