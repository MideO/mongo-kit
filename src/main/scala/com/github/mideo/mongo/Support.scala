package com.github.mideo.mongo

import reactivemongo.api.commands.WriteResult

import scala.concurrent._

trait CollectionItem {
  val identifier:String = "_id"

  def identifierValue:String = {
    val f = this.getClass.getDeclaredField(identifier)
    f.setAccessible(true)
    f.get(this).toString
  }
}

trait OpCreate[T] {
  protected def create(t: T): Future[WriteResult]
}

trait OpRead[T] {
  protected def read: Future[List[T]]
  protected def read(fieldValue: String): Future[List[T]]
}

trait OpUpdate[T] {
  protected def update(fieldValue: String, t: T): Future[WriteResult]
}

trait OpDelete[T] {
  protected def delete(fieldValue:String): Future[WriteResult]
}