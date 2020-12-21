import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object Application {
  def main(args: Array[String]): Unit = {

    // Get environment variables for spark and hadoop data source
    val sparkMaster = sys.env("ES_SPARK_MASTER")
    val dataSource = sys.env("ES_DATA_SOURCE")

    // Connect to spark master
    val spark = SparkSession.builder
      .appName("BigData - ExtraSensory")
      .master(sparkMaster)
      .getOrCreate()

    // Read from data source
    val df = spark.read.option("header", "true").csv(dataSource)

    // Set location and time range for application tasks
    val timeRange = new TimeRange(1441080000, 1459000000)
    val location = new Location(32.0, -117.0)
    val threshold = 1.0

    // Print time in minutes that users spent with friends
    printTimeWithPhoneInHand(df, timeRange, location, threshold)

    // Print time in minutes that users spent with phone in their hand
    printTimeWithFriends(df, timeRange, location, threshold)

    // Print normalization multiplier min, max and avg values
    printAudioStats(df, timeRange, location, threshold)

    spark.stop()
    spark.close()
  }

  def printTimeWithPhoneInHand(df: DataFrame, timeRange: TimeRange, location: Location, threshold: Double): Unit = {
    val label = "label:PHONE_IN_HAND"
    df
      .filter(row => isNearbyLocation(row, location, threshold))
      .filter(df.col("timestamp").between(timeRange.start, timeRange.end))
      .filter(df.col(label).cast(DoubleType).equalTo(1.0))
      .select(count(label).*(60).name("time with phone in hand (in minutes)"))
      .show()
  }

  def printTimeWithFriends(df: DataFrame, timeRange: TimeRange, location: Location, threshold: Double): Unit = {
    val label = "label:WITH_FRIENDS"
    df
      .filter(row => isNearbyLocation(row, location, threshold))
      .filter(df.col("timestamp").between(timeRange.start, timeRange.end))
      .filter(df.col(label).cast(DoubleType).equalTo(1.0))
      .select(count(label).*(60).name("time with friends (in minutes)"))
      .show()
  }

  def printAudioStats(df: DataFrame, timeRange: TimeRange, location: Location, threshold: Double): Unit = {
    val label = "audio_properties:normalization_multiplier"
    df
      .filter(row => isNearbyLocation(row, location, threshold))
      .filter(df.col("timestamp").between(timeRange.start, timeRange.end))
      .select(min(label), avg(label), max(label))
      .show()
  }

  def isNearbyLocation(row: Row, targetLocation: Location, threshold: Double): Boolean = {
    val lat = row.getAs[String]("latitude")
    val lon = row.getAs[String]("longitude")
    if (lat == null || lon == null)
      return false
    val location = new Location(lat.toDouble, lon.toDouble)
    location.isCloseTo(targetLocation, threshold)
  }
}
