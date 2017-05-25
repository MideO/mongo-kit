package com.github.mideo.mongo.inmemory

import com.github.mideo.mongo._
import reactivemongo.api.commands.{DefaultWriteResult, WriteError, WriteResult}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

trait InMemoryRepo[T] {
  protected val InMemoryCollection: ArrayBuffer[T] = ArrayBuffer[T]()
}


trait Create[T]
  extends InMemoryRepo[T]
    with OpCreate[T] {
  override def create(t: T): Future[WriteResult] = {
    InMemoryCollection += t
    Future {
      DefaultWriteResult(ok = true, 1, List(), None, None, None)
    }
  }
}

trait Read[T <: CollectionItem]
  extends InMemoryRepo[T]
    with OpRead[T] {
  override def read: Future[List[T]] = Future {
    InMemoryCollection.toList
  }

  override def read(fieldValue: String): Future[List[T]] = {
    Future {
      (InMemoryCollection filter { (c: CollectionItem) => {
        c.identifierValue.equals(fieldValue)
      }
      }).toList
    }
  }
}


trait Update[T <: CollectionItem]
  extends InMemoryRepo[T]
    with OpUpdate[T] {

  override def update(fieldValue: String, t: T): Future[WriteResult] = {
    val temp = InMemoryCollection filter { (c: CollectionItem) => !c.identifierValue.equals(fieldValue) }

    if (InMemoryCollection.size > temp.size) {
      InMemoryCollection += t
      return Future {
        DefaultWriteResult(ok = true, 1, List(), None, None, None)
      }
    }

    Future {
      DefaultWriteResult(ok = false, 1, List(WriteError(-1, -1, "document does not exist")), None, None, None)
    }
  }
}


trait Delete[T <: CollectionItem]
  extends InMemoryRepo[T]
    with OpDelete[T] {
  override def delete(fieldValue: String): Future[WriteResult] = {
    val temp = InMemoryCollection filter { (c: CollectionItem) => c.identifierValue.equals(fieldValue) }

    if (temp.isEmpty) {
      Future {
        DefaultWriteResult(ok = false, 1, List(WriteError(-1, -1, "document does not exist")), None, None, None)
      }
    }
    else {
      InMemoryCollection -= temp.head
      Future {
        DefaultWriteResult(ok = true, 1, List(), None, None, None)
      }
    }
  }
}


trait Crud[T <: CollectionItem]
  extends Create[T]
    with Read[T]
    with Update[T]
    with Delete[T]