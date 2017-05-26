package com.github.mideo.mongo

import com.github.mideo.mongo.inmemory.{Crud => InMemCrud}
import com.github.mideo.mongo.reactive.{Crud => ReactiveCrud}
import org.bson.types.ObjectId
import org.scalamock.scalatest.MockFactory
import org.scalatest.{AsyncFeatureSpec, GivenWhenThen}
import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi

class MongoKitSpec
  extends AsyncFeatureSpec
    with GivenWhenThen {

}

class CarRepo
  extends InMemCrud[Car]

object Car extends CollectionItem{
  //implicit val formatter: OFormat[Car] = Json.format[Car]
  def apply(colour: String): Car = {
    new Car(ObjectId.get, colour)
  }
}

case class Car(_id: ObjectId, colour: String, override val identifier: String = "colour")
  extends CollectionItem {
}

//
//class ReactiveCarRepo
//  extends ReactiveCrud[Car]with MockFactory {
//  override implicit val formatter: OFormat[Car] = Car.formatter
//  override val reactiveMongo: ReactiveMongoApi = mock[ReactiveMongoApi]
//  override val repoName: String = "car"
//}