package com.example.app.db

import com.example.app.AppConfig
import com.github.mideo.mongo.reactive.{Crud, ReactiveMongoApiWrapper}
import com.typesafe.config.Config
import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent._


object Card {
  implicit val formatter: OFormat[Card] = Json.format[Card]
}

case class Card(colour: String)

trait CardRepo {
  def read:Future[List[Card]]
}

class ReactiveCardRepo extends CardRepo with Crud[Card]{
  private val config: Config = AppConfig.config
  private val reactiveMongoApiWrapper = new ReactiveMongoApiWrapper(config, config.getString("mongo.uri"), config.getString("mongo.db"))

  override implicit def Writer: BSONDocumentWriter[Card] = Macros.writer[Card]

  override implicit def Reader: BSONDocumentReader[Card] = Macros.reader[Card]

  override val repoName: String = "card"

  override val reactiveMongo: ReactiveMongoApi = reactiveMongoApiWrapper.mongoApi

  override val collection: Future[BSONCollection] = reactiveMongoApiWrapper.mongoCollection(repoName)

}