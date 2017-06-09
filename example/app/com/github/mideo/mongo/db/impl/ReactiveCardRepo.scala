package com.github.mideo.mongo.db.impl

import com.github.mideo.mongo.db.{CardRepo, Card}
import com.github.mideo.mongo.reactive.Crud
import com.google.inject.Inject
import play.api.libs.json.OFormat
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReactiveCardRepo @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends CardRepo
    with Crud[Card] {
  override implicit val formatter: OFormat[Card] = Card.formatter
  override val reactiveMongo: ReactiveMongoApi = reactiveMongoApi
  override val repoName: String = "card"
  override val collection: Future[JSONCollection] = reactiveMongoApi.database map {_.collection[JSONCollection](repoName) }
}
