package com.github.mideo.mongo

import com.github.mideo.mongo.inmemory.{Crud => InMemCrud}
import com.github.mideo.mongo.reactive.{Crud => ReactiveCrud}
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFeatureSpec, GivenWhenThen, OneInstancePerTest}
import play.api.libs.json._
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent.ExecutionContext.Implicits.global
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



  abstract class ReactiveCarRepo
    extends ReactiveCrud[Car] {

    override val reactiveMongo: ReactiveMongoApi = mockReactiveMongoApi
    override val repoName: String = "car"


    override implicit def Writer: BSONDocumentWriter[Car] = Macros.writer[Car]

    override implicit def Reader: BSONDocumentReader[Car] = Macros.reader[Car]
  }

}