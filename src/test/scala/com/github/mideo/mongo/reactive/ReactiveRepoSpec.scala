package com.github.mideo.mongo.reactive

import com.github.mideo.mongo.{Car, MongoKitSpec}
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import play.api.libs.json.Json
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.{DefaultWriteResult, WriteResult}

import scala.concurrent._


class ReactiveRepoSpec extends MongoKitSpec {

  feature("ReactiveRepo Operation") {
    info("As a developer")
    info("I want to connect mongo db")
    info("So that I can run my  application with on a real database")
    val GlobalEC = scala.concurrent.ExecutionContext.Implicits.global

    scenario("Create") {
      When("I create a car in Repo")
      val car = Car("Pink")

      val repo:ReactiveCarRepo = new ReactiveCarRepo {
        override val collection: Future[BSONCollection] = mock[Future[BSONCollection]](new Answer[Future[DefaultWriteResult]] {
          override def answer(invocation: InvocationOnMock): Future[DefaultWriteResult] = Future (DefaultWriteResult(ok = true, 1, List(), None, None, None))
        })
      }

      val result: Future[WriteResult] = repo.create(car)


      Then("I expect a result")
      assert(result != null)

      result map {
        it =>
          assert(it.ok)
          assert(it.n == 1)

      }
    }

    scenario("Read") {
      When("I read the car Repo")


      val repo:ReactiveCarRepo = new ReactiveCarRepo {
        override val collection: Future[BSONCollection] = mock[Future[BSONCollection]](new Answer[Future[List[Car]]] {
          override def answer(invocation: InvocationOnMock): Future[List[Car]] = Future (List(Car("red")))
        })
      }
      val result: Future[List[Car]] = repo.read("colour", "red")


      Then("I expect a result")
      //Mockito.verify(mockCollection).find(fil)(Json.writes[JsObject])
      result map {
        it => assert(it.size == 1)
      }
    }

    scenario("Read All") {
      When("I read the car Repo")
      val repo:ReactiveCarRepo = new ReactiveCarRepo {
        override val collection: Future[BSONCollection] = mock[Future[BSONCollection]](new Answer[Future[List[Car]]] {
          override def answer(invocation: InvocationOnMock): Future[List[Car]] = Future (List(Car("red")))
        })
      }
      val result: Future[List[Car]] = repo.read


      Then("I expect a result")
      //Mockito.verify(mockCollection).find(fil)(Json.writes[JsObject])
      result map {
        it => assert(it.size == 1)
      }
    }


    scenario("Update") {
      When("I update a car in Repo")
      val car = Car("Pink")
      val repo:ReactiveCarRepo = new ReactiveCarRepo {
        override val collection: Future[BSONCollection] = mock[Future[BSONCollection]](new Answer[Future[DefaultWriteResult]] {
          override def answer(invocation: InvocationOnMock): Future[DefaultWriteResult] = Future (DefaultWriteResult(ok = true, 1, List(), None, None, None))
        })
      }

      val result: Future[WriteResult] = repo.update("colour", "red", car)


      Then("I expect reactivemongo update to be invoked")
      result map {
        it =>
          assert(it.ok)
          assert(it.n == 1)

      }

    }

    scenario("Delete") {
      When("I delete a car in Repo")
      val car = Car("Pink")
      val repo:ReactiveCarRepo = new ReactiveCarRepo {
        override val collection: Future[BSONCollection] = mock[Future[BSONCollection]](new Answer[Future[DefaultWriteResult]] {
          override def answer(invocation: InvocationOnMock): Future[DefaultWriteResult] = Future (DefaultWriteResult(ok = true, 1, List(), None, None, None))
        })
      }

      val result: Future[WriteResult] = repo.delete("colour", "red")

      Then("I expect a result")

      result map {
        it =>
          assert(it.ok)
          assert(it.n == 1)

      }
    }
  }

}
