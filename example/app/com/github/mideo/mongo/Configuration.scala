package com.github.mideo.mongo

import com.github.mideo.mongo.db.CarRepo
import com.github.mideo.mongo.db.impl.ReactiveCarRepo
import com.google.inject.AbstractModule


class Configuration extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[CarRepo]).to(classOf[ReactiveCarRepo])
  }
}
