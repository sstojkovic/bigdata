import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY

class DataStore {
  var events: MongoCollection[EventStats] = null
  var locations: MongoCollection[LocationStats] = null

  def this(connectionString: String) = {
    this()
    val mongoClient: MongoClient = MongoClient(connectionString)
    val codecRegistry = fromRegistries(fromProviders(classOf[EventStats], classOf[LocationStats]), DEFAULT_CODEC_REGISTRY )
    val database: MongoDatabase = mongoClient.getDatabase("project2").withCodecRegistry(codecRegistry)
    events = database.getCollection("events")
    locations = database.getCollection("locations")
  }

  def persistEventStats(eventStats: EventStats): Unit = {
    events.insertOne(eventStats).subscribe(x => println("Inserted event stats"))
  }

  def persistLocationStats(locationStats: Array[LocationStats]): Unit = {
    locations.insertMany(locationStats).subscribe(x => println("Inserted location stats"))
  }
}
