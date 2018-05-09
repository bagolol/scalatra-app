# scalatra-let-shout #

## Run ##

Add these env variables
```sh
export TWITTER_CONSUMER_KEY=yourKey
export TWITTER_CONSUMER_SECRET=yourSecret
export TWITTER_ACCESS_TOKEN=yourTwitterAccessToken
export TWITTER_TOKEN_SECRET=yourTwitterTokenSecret
```
Start the server
```sh
$ sbt
> jetty:start
```
Open [http://localhost:8080/](http://localhost:8080/) in your browser

To look for tweets for a specific username open: `http://localhost:8080/tweets/<username>/<limit>`

## Unit tests ##

```sh
$ sbt test
```
## Cucumber tests ##

```sh
$ sbt cucumber
```


# Technology #

I decided to use Scalatra because it's a framework that I've used before, it's quite thin and looked appropriate for a simple API.
In designing the functionality I decided to keep all the concern separated, so that the servlet layer is only responsible for receiving and responding to requests, the service layer is responsible for the business logic and the dao is just a very thin layer on top of the Twitter rest client. I decided to use a library to handle the Twitter requests, because it took care of authenticating the requests and because it had exactly the method I needed to return the tweets. However, if I was to execute more complicated queries, I'd probably want to create my own client, which would make error handling and testing a bit easier.

I chose not to unit test the endpoints, because those were covered by the cucumber tests. 

# Reflections #

It took me overall about two and a half days to complete the test, which was more than I expected. However, setting up the project and the tests (Cucumber tests in particular) took quite long since it is a task that I rarely do. Also, I wasted some time trying to work out why my mock wasn't being called and later discovered that it was due to a typo. I assume this wouldn't have happened in a working environment either becuase of pair programming or because I would have asked for help much earlier. Overall it was an interesting exercise. 



