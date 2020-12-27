import org.mongodb.scala.bson.ObjectId

object EventStats extends Serializable {
  var name: String = ""
  var min: Double = 0
  var max: Double = 0
  var mean: Double = 0
  var count: Long = 0

  def apply(name: String, min: Double, max: Double, mean: Double, count: Long): EventStats = {
    EventStats(new ObjectId(), name, min, max, mean, count)
  }

}

case class EventStats(_id: ObjectId, name: String, min: Double, max: Double, mean: Double, count: Long) {
  def print(): Unit = {
    println("==========================")
    println("Name: " + name)
    println("Min: " + min)
    println("Max: " + max)
    println("Mean: " + mean)
    println("Count: " + count)
    println("==========================")
  }
}
