package com.github.mideo.mongo.reactive

import com.github.mideo.mongo.{Car, MongoKitSpec}
import org.mockito.Mockito
import play.api.libs.json._
import reactivemongo.api.commands.{DefaultWriteResult, UpdateWriteResult, WriteResult}

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
      Mockito.when(mockCollection.insert(car)(ReactiveCarRepo.formatter, GlobalEC)).
        thenReturn(Future {DefaultWriteResult(ok = true, 1, List(), None, None, None)})
      val result: Future[WriteResult] = ReactiveCarRepo.create(car)


      Then("I expect result to be ok")
      result map {
        it =>
          assert(it.ok)
          assert(it.n == 1)

      }
    }

    scenario("Read") {
      When("I read the car Repo")
      val car = Car("Pink")
      val fil = Json.obj(car.identifier -> car.identifierValue)
      val result: Future[List[Car]] = ReactiveCarRepo.read(car.identifier, car.identifierValue)


      Then("I expect a result")
      //Mockito.verify(mockCollection).find(fil)(Json.writes[JsObject])
      assert(result != null)
    }

    scenario("Read All") {
      When("I read the car Repo")
      val result = ReactiveCarRepo.read


      Then("I expect a result")
      assert(result != null)
    }



    scenario("Update") {
      When("I update a car in Repo")
      val car = Car("Pink")
      val fil = Json.obj(car.identifier -> "red")
      val result: Future[WriteResult] = ReactiveCarRepo.update("red", car)


      Then("I expect reactivemongo update to be invoked")
      Mockito.verify(mockCollection).update(fil, car)(Json.writes[JsObject], ReactiveCarRepo.formatter, GlobalEC)
      assert(result != null)

    }

    scenario("Delete") {
      When("I delete a car in Repo")
      val car = Car("Pink")
      val fil = Json.obj(car.identifier -> car.identifierValue)
      val result: Future[WriteResult] = ReactiveCarRepo.delete(car.identifier, car.identifierValue)

      Then("I expect a result")
      Mockito.verify(mockCollection).remove(fil)(Json.writes[JsObject], GlobalEC)
      assert(result != null)
    }
  }

}
