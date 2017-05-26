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

trait OpCreate[A] {
  protected def create(a: A): Future[WriteResult]
}

trait OpRead[A] {
  protected def read: Future[List[A]]
  protected def read(fieldValue: String): Future[List[A]]
}

trait OpUpdate[A] {
  protected def update(fieldValue: String, a: A): Future[WriteResult]
}

trait OpDelete[A] {
  protected def delete(fieldValue:String): Future[WriteResult]
}

