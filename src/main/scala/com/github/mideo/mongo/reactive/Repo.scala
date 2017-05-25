package com.github.mideo.mongo.reactive

import com.github.mideo.mongo.CollectionItem
import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.play.json._

trait ReactiveRepo[T] {
  implicit val formatter:OFormat[T]
  val reactiveMongo: ReactiveMongoApi
  val repoName:String
  def collection:Future[JSONCollection] = reactiveMongo.database map {_.collection[JSONCollection](repoName)}
}

trait Create[T<: CollectionItem] extends ReactiveRepo[T] {
  def create(t: T): Future[WriteResult] = {
    collection flatMap {
      _.insert(t)
    }
  }
}

trait Read[T<: CollectionItem] extends ReactiveRepo[T] {
  def read(field:String, value: String): Future[List[T]] = {

    collection.flatMap {
      _.find(Json.obj( field -> value)).
        cursor[T](ReadPreference.primary).
        collect[List](-1, Cursor.FailOnError[List[T]]())
    }
  }

  def readAll:Future[List[T]] = {
    collection.flatMap {
      _.find(Json.obj()).
        cursor[T](ReadPreference.primary).
        collect[List](-1, Cursor.FailOnError[List[T]]())
    }
  }
}

trait Update[T<: CollectionItem] extends ReactiveRepo[T] {
  def update(value:String, t:T): Future[WriteResult] = {
    collection.flatMap { _.update(Json.obj(t.identifierValue -> value), t)}
  }
}

trait Delete[T<: CollectionItem] extends ReactiveRepo[T] {
  def delete(field:String, value: String): Future[WriteResult] = {
    collection.flatMap{ _.remove(Json.obj(field -> value))}
  }
}

trait Crud[T<: CollectionItem]
  extends Create[T]
    with Read[T]
    with Update[T]
    with Delete[T]
