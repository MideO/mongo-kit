package com.github.mideo.mongo.db.impl

import com.github.mideo.mongo.db.{Car, CarRepo}
import com.github.mideo.mongo.reactive.Crud
import com.google.inject.Inject
import play.api.libs.json.OFormat
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReactiveCarRepo @Inject()(val reactiveMongoApi: ReactiveMongoApi)
  extends CarRepo
    with Crud[Car] {
  override implicit val formatter: OFormat[Car] = Car.formatter
  override val reactiveMongo: ReactiveMongoApi = reactiveMongoApi
  override val repoName: String = "car"
  override val collection: Future[JSONCollection] = reactiveMongo.database map {
    _.collection[JSONCollection](repoName)
  }
}
