class Location extends Serializable {
  var latitude: Double = 0
  var longitude: Double = 0

  def this(latitude: Double, longitude: Double) = {
    this()
    this.latitude = latitude
    this.longitude = longitude
  }

  def isCloseTo(location: Location, threshold: Double): Boolean = {
    val lat = math.abs(this.latitude - location.latitude) < threshold
    val lon = math.abs(this.longitude - location.longitude) < threshold
    val closeTo = lat && lon
    closeTo
  }
}
