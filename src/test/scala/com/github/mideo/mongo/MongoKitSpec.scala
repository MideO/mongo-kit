package com.github.mideo.mongo

import com.github.mideo.mongo.inmemory.{Crud => InMemCrud}
import com.github.mideo.mongo.reactive.{Crud => ReactiveCrud}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFeatureSpec, GivenWhenThen, OneInstancePerTest}
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object Car {
  def apply(colour: String): Car = new Car(colour)
}


case class Car(colour: String, override val identifier: String = "colour") extends CollectionItem

class CarRepo extends InMemCrud[Car]

class MongoKitSpec
  extends AsyncFeatureSpec
    with GivenWhenThen
    with OneInstancePerTest with MockitoSugar {
  val mockReactiveMongoApi: ReactiveMongoApi = mock[ReactiveMongoApi]
  val mockCollection:JSONCollection =   mock[JSONCollection]

object ReactiveCarRepo
    extends ReactiveCrud[Car] with MockitoSugar{
    implicit val formatter: OFormat[Car] = Json.format[Car]
    override val reactiveMongo: ReactiveMongoApi = mockReactiveMongoApi
    override val repoName: String = "car"
    override val collection: Future[JSONCollection] = Future{mockCollection}
  }
}