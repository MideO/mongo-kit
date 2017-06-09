package com.github.mideo.mongo.reactive

import com.github.mideo.mongo._
import play.api.libs.json.{Json, OFormat}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

trait ReactiveRepo[A] extends FieldValueGetter[A] {
  implicit val formatter: OFormat[A]
  val reactiveMongo: ReactiveMongoApi
  val repoName: String
  val collection: Future[JSONCollection]

}

trait Create[A] extends ReactiveRepo[A] with OpCreate[A] {
  def create(a: A): Future[WriteResult] = {
    collection flatMap {
      _.insert(a)
    }
  }
}

trait Read[A] extends ReactiveRepo[A] with OpRead[A] {
  def read(field: String, value: String): Future[List[A]] = {

    collection.flatMap {
      _.find(Json.obj(field -> value)).
        cursor[A](ReadPreference.primary).
        collect[List](-1, Cursor.FailOnError[List[A]]())
    }
  }

  def read: Future[List[A]] = {
    collection.flatMap {
      _.find(Json.obj()).
        cursor[A](ReadPreference.primary).
        collect[List](-1, Cursor.FailOnError[List[A]]())
    }
  }
}

trait Update[A] extends ReactiveRepo[A] with OpUpdate[A] {
  def update(field: String, value: String, a: A): Future[WriteResult] = {
    collection.flatMap {
      _.update(Json.obj(field -> value), a)
    }
  }
}

trait Delete[A] extends ReactiveRepo[A] with OpDelete[A] {
  def delete(field: String, value: String): Future[WriteResult] = {
    collection.flatMap {
      _.remove(Json.obj(field -> value))
    }
  }
}

trait Crud[A]
  extends Create[A]
    with Read[A]
    with Update[A]
    with Delete[A]
