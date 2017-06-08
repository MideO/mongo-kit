package com.github.mideo.mongo.inmemory

import com.github.mideo.mongo._
import reactivemongo.api.commands.{DefaultWriteResult, WriteError, WriteResult}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

trait InMemoryRepo[A] {
  protected val InMemoryCollection: ArrayBuffer[A] = ArrayBuffer[A]()
}


trait Create[A]
  extends InMemoryRepo[A]
    with OpCreate[A] {
  override def create(a: A): Future[WriteResult] = {
    InMemoryCollection += a
    Future {
      DefaultWriteResult(ok = true, 1, List(), None, None, None)
    }
  }
}

trait Read[A <: CollectionItem]
  extends InMemoryRepo[A]
    with OpRead[A] {
  override def read: Future[List[A]] = Future {
    InMemoryCollection.toList
  }

  override def read(field:String, value: String): Future[List[A]] = {
    Future {
      (InMemoryCollection filter { (c: CollectionItem) => {
        c.identifier.equals(field) && c.identifierValue.equals(value)
      }
      }).toList
    }
  }
}


trait Update[A <: CollectionItem]
  extends InMemoryRepo[A]
    with OpUpdate[A] {

  override def update(fieldValue: String, a: A): Future[WriteResult] = {
    val temp = InMemoryCollection filter { (c: CollectionItem) => !c.identifierValue.equals(fieldValue) }

    if (InMemoryCollection.size > temp.size) {
      InMemoryCollection += a
      return Future {
        DefaultWriteResult(ok = true, 1, List(), None, None, None)
      }
    }

    Future {
      DefaultWriteResult(ok = false, 1, List(WriteError(-1, -1, "document does not exist")), None, None, None)
    }
  }
}


trait Delete[A <: CollectionItem]
  extends InMemoryRepo[A]
    with OpDelete[A] {
  override def delete(field:String, value: String): Future[WriteResult] = {
    val temp = InMemoryCollection filter { (c: CollectionItem) =>
          c.identifier.equals(field) && c.identifierValue.equals(value)
    }

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


trait Crud[A <: CollectionItem]
  extends Create[A]
    with Read[A]
    with Update[A]
    with Delete[A]