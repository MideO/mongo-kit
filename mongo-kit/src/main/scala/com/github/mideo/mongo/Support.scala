package com.github.mideo.mongo

import reactivemongo.api.commands.WriteResult

import scala.concurrent._

trait FieldValueGetter[A] {

  def getFieldValue(field: String, a: A): String = {
    val f = a.getClass.getDeclaredField(field)
    f.setAccessible(true)
    f.get(a).toString
  }
}

trait OpCreate[A] {
  protected def create(a: A): Future[WriteResult]
}

trait OpRead[A] {
  protected def read: Future[List[A]]

  protected def read(field: String, value: String): Future[List[A]]
}

trait OpUpdate[A] {
  protected def update(field: String, value: String, a: A): Future[WriteResult]
}

trait OpDelete[A] {
  protected def delete(field: String, value: String): Future[WriteResult]
}

