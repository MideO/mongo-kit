package com.github.mideo.mongo

import com.github.mideo.mongo.db.CardRepo
import com.google.inject.AbstractModule

class TestConfiguration extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CardRepo]).to(classOf[InMemoryDBCardRepo])
  }
}
