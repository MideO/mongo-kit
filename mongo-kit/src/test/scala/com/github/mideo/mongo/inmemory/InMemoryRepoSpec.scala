package com.github.mideo.mongo.inmemory

import com.github.mideo.mongo.{Car, CarRepo, MongoKitSpec}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

class InMemoryRepoSpec extends MongoKitSpec {

  feature("InMemoryRepo Operation") {
    info("As a developer")
    info("I want an in memory mongo db")
    info("So that I can run my  application tests without dependency on a real database")

    scenario("Create ") {
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()

      When("I create a car")
      val result: Future[WriteResult] = carRepo.create(Car("Red"))


      Then("I expect the Car to be stored successfully")
      result map { it => assert(it.ok) }

    }


    scenario("Read"){
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()
      val car = Car("Red")


      When("I create a car")
      carRepo.create(car)

      And("I search the car repository")
      val result = carRepo.read("colour",  "Red")


      Then("I expect the car to be returned")
      result map {
        it => assert(it.size == 1)
              assert(it.head.colour == "Red")
      }
    }

    scenario("Read All"){
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()
      
      When("I create cars")
      carRepo.create(Car("Red"))
      carRepo.create(Car("Orange"))
      carRepo.create(Car("Green"))
      carRepo.create(Car("Blue"))
      
      And("I search for cars")
      val result = carRepo.read


      Then("I expect the car to be returned")
      result map {
        it => assert(it.size == 4)
          
      }
    }

    scenario("Update") {
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()
      val car = Car("Red")

      And("I create as car")
      carRepo.create(car)

      When("I update the car colour")
      val result: Future[WriteResult] = carRepo.update("colour", car.colour, Car("Blue"))

      Then("I expect the Car to be stored successfully")
      result map { it => assert(it.ok) }


      And("I search the car repository")
      val result1 = carRepo.read("colour", "Blue")


      Then("I expect the car to be returned")
      result1 map {
        it => assert(it.size == 1)
          assert(it.head.colour == "Blue")
      }


    }

    scenario("Update non-existent car") {
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()

      When("I update a non existent car")
      val result: Future[WriteResult] = carRepo.update("colour","Green", Car("Blue"))

      Then("I expect a failed result")
      result map {
        it => assert(!it.ok)
          assert(it.writeErrors.toList.head.errmsg == "document does not exist")
      }

    }


    scenario("Delete"){
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()

      When("I create a car")
      val car  = Car("Red")
      carRepo.create(Car("Red"))

      And("I delete the car")
      val result = carRepo.delete("colour", "Red")


      Then("I expect the car to be deleted successfully")
      result map { it => assert(it.ok) }

    }

    scenario("Delete non-existent car"){
      Given("An InMemory Car Repository")
      val carRepo: CarRepo = new CarRepo()

      When("I delete a non existent car")
      val car  = Car("Red")
      val result = carRepo.delete("colour", "Red")


      Then("I expect a failed result")
      result map {
        it => assert(!it.ok)
              assert(it.writeErrors.toList.head.errmsg == "document does not exist")
      }

    }
  }
}
