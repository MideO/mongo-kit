package com.github.mideo.mongo.reactive

import com.typesafe.config.Config
import play.api.Configuration
import play.api.inject.{ApplicationLifecycle, DefaultApplicationLifecycle}
import play.modules.reactivemongo.{DefaultReactiveMongoApi, ReactiveMongoApi}
import reactivemongo.api.MongoConnection
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.collections.bson.BSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReactiveMongoApiWrapper(config: Config, connectionString: String, dataBaseName: String) {
  private val lifeCycle: ApplicationLifecycle = new DefaultApplicationLifecycle()
  private val conf: Configuration = new Configuration(config)
  private val parsedURI: ParsedURI = MongoConnection.parseURI(connectionString).get
  private val db: String = dataBaseName

  val mongoApi: ReactiveMongoApi = new DefaultReactiveMongoApi("default", parsedURI, db, true, conf, lifeCycle)

  def mongoCollection(repositoryName: String): Future[BSONCollection] = {
    mongoApi.connection.database(dataBaseName) map {
      _.collection[BSONCollection](repositoryName)
    }
  }

}
