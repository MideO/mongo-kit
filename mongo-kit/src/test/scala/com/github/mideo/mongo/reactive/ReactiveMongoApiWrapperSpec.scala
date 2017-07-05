package com.github.mideo.mongo.reactive

import com.github.mideo.mongo.MongoKitSpec
import com.typesafe.config.ConfigFactory
import play.modules.reactivemongo.ReactiveMongoApi

class ReactiveMongoApiWrapperSpec extends MongoKitSpec {


  feature("ReactiveMongoApiWrapper") {
    info("As a developer not using play")
    info("I want to create a reactiveMongoApi")
    info("So that I can use mongokit")

    scenario("Create Reactive") {
      When("I create an instance of ReactiveMongoApiWrapper")
      val wrapper: ReactiveMongoApiWrapper = new ReactiveMongoApiWrapper(ConfigFactory.load(), "mongodb://lcoalhost:27017", "bazz")

      Then("I should have a reactiveMongoApi instance")
      assert(wrapper.mongoApi.isInstanceOf[ReactiveMongoApi])

    }
  }

}
