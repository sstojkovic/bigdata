import org.mongodb.scala.bson.ObjectId

object LocationStats extends Serializable {
  var latitude: Double = 0
  var longitude: Double = 0
  var count: Int = 0

  def apply(latitude: Double, longitude: Double, count: Int): LocationStats = {
    LocationStats(new ObjectId(), latitude, longitude, count)
  }
}

case class LocationStats(_id: ObjectId, latitude: Double, longitude: Double, count: Int) {
  def print(): Unit = {
    println("=====================================")
    println(s"Most popular location")
    println(s"Latitude: ${latitude}")
    println(s"Longitude: ${longitude}")
    println(s"Number of events: ${count}")
    println("=====================================")
  }
}
