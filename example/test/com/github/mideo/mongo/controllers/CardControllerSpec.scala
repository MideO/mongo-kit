package com.github.mideo.mongo.controllers

import com.github.mideo.mongo.db.Card
import com.github.mideo.mongo.{UnitSpec, WithInMemoryDbApplication}
import play.api.libs.json.Json
import play.api.test.{FakeHeaders, FakeRequest}
import play.api.test.Helpers.{GET, contentAsJson, contentType, route, status}

import play.api.http.Status._
import scala.concurrent.duration._
import akka.util.Timeout



class CardControllerSpec extends UnitSpec {
  implicit val duration: Timeout = 2 second

  "CardControllerSpec" should {
    "create and return card" in new WithInMemoryDbApplication {
      //When
      val Some(postResult) = route(app, FakeRequest("POST", "/", FakeHeaders(), body = Json.toJson(Card("red"))))

      //Then
      status(postResult) must be equalTo CREATED

      //When
      val Some(getResult) = route(app, FakeRequest("GET", "/", FakeHeaders(), body = ""))


      //Then
      status(getResult) must be equalTo OK
      contentType(getResult).get must be equalTo "application/json"
      contentAsJson(getResult) must be equalTo Json.toJson(List(Card("red")))
    }

  }
}
