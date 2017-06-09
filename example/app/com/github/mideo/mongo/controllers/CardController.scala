package com.github.mideo.mongo.controllers

import javax.inject._

import com.github.mideo.mongo.Errors
import com.github.mideo.mongo.db.{Card, CardRepo}
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import reactivemongo.api.commands.WriteResult
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

@Singleton
class CardController @Inject()(cardRepo: CardRepo) extends Controller {

  def createFromJson: Action[JsValue] = Action.async(parse.json) {
    request => Json.fromJson[Card](request.body) match {
      case JsSuccess(card, _) =>
        Logger.info(s"$card")
        cardRepo.create(card).map {
          result: WriteResult =>
            Logger.info(s"$result")
            if (!result.ok) {
              PreconditionFailed(s"Could not create ${card.colour}")
            } else {
              Created(s"Created 1 card")
            }
        }
      case JsError(errors) =>
        Future.successful(BadRequest(Errors.showJsonErrors(errors)))

    }
  }

  def findAll: Action[AnyContent] = Action.async {
    cardRepo.read.map {
      (cards: List[Card]) => Ok(Json.toJson(cards))
    }
  }
}
