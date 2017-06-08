package com.github.mideo.mongo

import com.github.mideo.mongo.db.{Car, CarRepo}
import com.github.mideo.mongo.inmemory.Crud
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.WithApplication


abstract class UnitSpec
  extends Specification
    with Mockito

class InMemoryDB
  extends CarRepo
    with Crud[Car]

trait WithInMemoryDbApplication extends WithApplication {
  override val app = new GuiceApplicationBuilder().overrides(new TestConfiguration).build()
}
