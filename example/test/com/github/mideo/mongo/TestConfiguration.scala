package com.github.mideo.mongo

import com.github.mideo.mongo.db.CarRepo
import com.github.mideo.mongo.db.impl.ReactiveCardRepo
import com.google.inject.AbstractModule

class TestConfiguration extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CarRepo]).to(classOf[ReactiveCardRepo])
  }
}
