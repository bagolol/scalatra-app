package com.letshout.services

import org.json4s.JsonAST.JObject
import org.json4s.jackson.Json

trait TweetService {

  def capitaliseTweets(username:String, limit:String): List[JObject] = {



  }



}

object TweetService extends TweetService {


}