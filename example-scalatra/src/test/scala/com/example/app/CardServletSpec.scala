package com.example.app

import com.example.app.db.{Card, CardRepo}
import com.github.mideo.mongo.inmemory.Crud
import org.scalatra.test.specs2._


class InMemoryCarCrud extends CardRepo with Crud[Card]

class CardServletSpec extends MutableScalatraSpec {

  val cardServlet: CardServlet = new CardServlet(new InMemoryCarCrud)
  addServlet(cardServlet, "/*")

  "GET / on CardServlet" should {
    "return status 200" in {
      get("/") {
        status must_== 200
      }

    }
  }

}
