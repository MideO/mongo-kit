package com.example.app

import akka.actor.ActorSystem
import com.example.app.db.{Card, CardRepo}
import org.scalatra._
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

class CardServlet (cardRepo:CardRepo)  extends ScalatraServlet with FutureSupport {
  implicit val system = ActorSystem("MySystem")
  override protected implicit def executor: ExecutionContext = system.dispatcher

  get("/") {
    cardRepo.read.map {
      (cards: List[Card]) => Json.toJson(cards)
    }
  }


}
