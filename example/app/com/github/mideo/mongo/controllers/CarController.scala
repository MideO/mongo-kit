package com.github.mideo.mongo.controllers

import javax.inject._

import com.github.mideo.mongo.Errors
import com.github.mideo.mongo.db.{Car, CarRepo}
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

@Singleton
class CarController @Inject()(carRepo: CarRepo) extends Controller {

  def createFromJson: Action[JsValue] = Action.async(parse.json) {
    request => Json.fromJson[Car](request.body) match {
      case JsSuccess(car, _) =>
        carRepo.create(car).map {
          result: WriteResult =>
            Logger.info(s"$result")
            if (!result.ok) {
              PreconditionFailed(s"Could not create ${car.colour}")
            } else {
              Created(s"Created 1 car")
            }
        }
      case JsError(errors) =>
        Future.successful(BadRequest(Errors.showJsonErrors(errors)))

    }
  }

  def findAll: Action[AnyContent] = Action.async {
    carRepo.read.map {
      (cars: List[Car]) => Ok(Json.toJson(cars))
    }
  }
}
