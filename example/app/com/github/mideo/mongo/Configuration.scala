package com.github.mideo.mongo

import com.github.mideo.mongo.db.CardRepo
import com.github.mideo.mongo.db.impl.ReactiveCardRepo
import com.google.inject.AbstractModule


class Configuration extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[CardRepo]).to(classOf[ReactiveCardRepo])
  }
}
