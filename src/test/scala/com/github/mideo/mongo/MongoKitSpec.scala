package com.github.mideo.mongo

import org.bson.types.ObjectId
import org.scalatest.{AsyncFeatureSpec, GivenWhenThen}

class MongoKitSpec
  extends AsyncFeatureSpec
    with GivenWhenThen {

}

class CarRepo
  extends Crud[Car]

object Car {
  def apply(colour: String): Car = {
    new Car(ObjectId.get, colour)
  }
}

case class Car(_id: ObjectId, colour: String) extends CollectionItem {
  override val identifier: String = "colour"
}
