##  Mongo Kit - (WIP)

[![Build Status](https://travis-ci.org/MideO/mongo-kit.svg?branch=master)](https://travis-ci.org/MideO/mongo-kit)


### Setup dependency
Scala 2.11.7


Simple API abstraction to quick integration of mongodb CRUD operations.

### Usage
Full example in
 * example-playframework
 * example-scalatra (wip)
 
```scala
import com.github.mideo.mongo.reactive.{Crud => ReactiveCrud}]
import com.github.mideo.mongo.inmemory.{Crud => InMemoryCrud}
import com.google.inject.Inject
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Card { implicit val formatter: OFormat[Card] = Json.format[Card] }

case class Card(colour: String)


class ReactiveCardRepo (reactiveMongoApi: ReactiveMongoApi) #Create a document in mongo  
    extend ReactiveCrud[Card] {
  
  override val reactiveMongo: ReactiveMongoApi = reactiveMongoApi
  override val repoName: String = "card"
  override val collection: Future[BSONCollection] = reactiveMongo.database map {_.collection[BSONCollection](repoName) }

  override implicit def Writer: BSONDocumentWriter[Card] = Macros.writer[Card]

  override implicit def Reader: BSONDocumentReader[Card] = Macros.reader[Card]
}

val reactiveCardRepo: ReactiveCardRepo = new ReactiveCardRepo(new DefaultReactiveMongoApi(.....))
reactiveCardRepo.create(Card("green"))

#Test with in-memory db
class InMemoryCardRepo extends InMemoryCrud[Card] 

val inMemoryCardRepo: InMemoryCardRepo = new InMemoryCardRepo()
inMemoryCardRepo.create(Car("Red"))
```



