package com.github.mideo.mongo

import com.github.mideo.mongo.inmemory.{Crud => InMemCrud}
import com.github.mideo.mongo.reactive.{Crud => ReactiveCrud}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFeatureSpec, GivenWhenThen, OneInstancePerTest}
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent._

object Car {
  implicit val formatter: OFormat[Car] = Json.format[Car]
}
case class Car(colour: String)

class CarRepo extends InMemCrud[Car]

class MongoKitSpec
  extends AsyncFeatureSpec
    with GivenWhenThen
    with OneInstancePerTest with MockitoSugar {
  val mockReactiveMongoApi: ReactiveMongoApi = mock[ReactiveMongoApi]
  val mockCollection:BSONCollection =   mock[BSONCollection]

object ReactiveCarRepo
    extends ReactiveCrud[Car] with MockitoSugar{
    //implicit val formatter: OFormat[Car] = Car.formatter
    override val reactiveMongo: ReactiveMongoApi = mockReactiveMongoApi
    override val repoName: String = "car"
    override def collection: Future[BSONCollection] = Future{mockCollection}

  override implicit def Writer: BSONDocumentWriter[Car] = Macros.writer[Car]

  override implicit def Reader: BSONDocumentReader[Car] = Macros.reader[Car]
}
}