package com.github.mideo.mongo.db

import play.api.libs.json.{Json, OFormat}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future


object Card {
  implicit val formatter: OFormat[Card] = Json.format[Card]
}

case class Card(colour: String)

trait CardRepo {
  def create(card:Card): Future[WriteResult]
  def read(field:String, value: String): Future[List[Card]]
  def read: Future[List[Card]]
  def update(field:String, value: String, book:Card): Future[WriteResult]
  def delete(field:String, value: String): Future[WriteResult]

}
