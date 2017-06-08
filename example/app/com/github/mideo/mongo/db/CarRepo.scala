package com.github.mideo.mongo.db

import com.github.mideo.mongo.CollectionItem
import play.api.libs.json.{Json, OFormat}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future


object Car {
  implicit val formatter: OFormat[Car] = Json.format[Car]
}

case class Car(colour: String) //extends CollectionItem("colour")

trait CarRepo {
  def create(car:Car): Future[WriteResult]
  def read(field:String, value: String): Future[List[Car]]
  def read: Future[List[Car]]
  def update(identifierValue:String, book:Car): Future[WriteResult]
  def delete(field:String, value: String): Future[WriteResult]

}
