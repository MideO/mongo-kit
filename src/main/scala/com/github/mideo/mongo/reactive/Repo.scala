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
trait ReactiveRepo[A] {
  implicit val formatter:OFormat[A]
  val reactiveMongo: ReactiveMongoApi
  val repoName:String
  def collection:Future[JSONCollection] = reactiveMongo.database map {_.collection[JSONCollection](repoName)}
}

trait Create[A<: CollectionItem] extends ReactiveRepo[A] {
  def create(a: A): Future[WriteResult] = {
    collection flatMap {
      _.insert(a)
    }
  }
}

trait Read[A<: CollectionItem] extends ReactiveRepo[A] {
  def read(field:String, value: String): Future[List[A]] = {

    collection.flatMap {
      _.find(Json.obj( field -> value)).
        cursor[A](ReadPreference.primary).
        collect[List](-1, Cursor.FailOnError[List[A]]())
    }
  }

  def readAll:Future[List[A]] = {
    collection.flatMap {
      _.find(Json.obj()).
        cursor[A](ReadPreference.primary).
        collect[List](-1, Cursor.FailOnError[List[A]]())
    }
  }
}

trait Update[A<: CollectionItem] extends ReactiveRepo[A] {
  def update(value:String, a:A): Future[WriteResult] = {
    collection.flatMap { _.update(Json.obj(a.identifierValue -> value), a)}
  }
}

trait Delete[A<: CollectionItem] extends ReactiveRepo[A] {
  def delete(field:String, value: String): Future[WriteResult] = {
    collection.flatMap{ _.remove(Json.obj(field -> value))}
  }
}

trait Crud[A<: CollectionItem]
  extends Create[A]
    with Read[A]
    with Update[A]
    with Delete[A]
