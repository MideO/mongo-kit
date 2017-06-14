package com.github.mideo.mongo.db.impl

import com.github.mideo.mongo.db.{Card, CardRepo}
import com.github.mideo.mongo.reactive.Crud
import com.google.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReactiveCardRepo @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends CardRepo
    with Crud[Card] {

  override val reactiveMongo: ReactiveMongoApi = reactiveMongoApi
  override val repoName: String = "card"
  override val collection: Future[BSONCollection] = reactiveMongo.database map {_.collection[BSONCollection](repoName) }

  override implicit def Writer: BSONDocumentWriter[Card] = Macros.writer[Card]

  override implicit def Reader: BSONDocumentReader[Card] = Macros.reader[Card]
}
