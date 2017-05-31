package com.github.mideo.mongo

import com.github.mideo.mongo.inmemory.{Crud => InMemCrud}
import com.github.mideo.mongo.reactive.{Crud => ReactiveCrud}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{AsyncFeatureSpec, GivenWhenThen}
import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.DefaultDB
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class MongoKitSpec
  extends AsyncFeatureSpec
    with GivenWhenThen {

}

class CarRepo extends InMemCrud[Car]

object Car {
  def apply(colour: String): Car = new Car(colour)
}


case class Car(colour: String, override val identifier: String = "colour") extends CollectionItem


class ReactiveCarRepo
  extends ReactiveCrud[Car]
    with MockFactory {
  override val reactiveMongo: ReactiveMongoApi = stub[ReactiveMongoApi]
  override implicit val formatter: OFormat[Car] = Json.format[Car]
  override val repoName: String = "car"

  val defaultDB: DefaultDB = stub[DefaultDB]
  (reactiveMongo.database _).when().returns(Future(defaultDB))
}