package com.example.app

import com.example.app.db.{CardRepo, ReactiveCardRepo}
import com.google.inject.{AbstractModule, Guice, Injector}
import com.typesafe.config.{Config, ConfigFactory}

class AppModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CardRepo]).to(classOf[ReactiveCardRepo])
  }
}

object AppConfig {
  val config: Config = ConfigFactory.load()
  val injector:Injector  = Guice.createInjector(new AppModule())

}