import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010._

import scala.collection.mutable

object Application {
  def main(args: Array[String]): Unit = {
    val sparkMaster = sys.env("ES_SPARK_MASTER")
    val kafkaServer = sys.env("ES_KAFKA_SERVER")
    val kafkaPort = sys.env("ES_KAFKA_PORT")
    val mongoConnectionString = sys.env("ES_MONGODB_CONNECTION_STRING")

    val windowDuration = Seconds(10)
    val slidingDuration = Seconds(10)
    val numberOfMostPopularLocationsToStore = 3
    val locationMatchingThreshold = 0.001
    val topic = "ExtraSensory"

    val conf = new SparkConf()
      .setAppName("[Project2] Kafka Consumer")
      .setMaster(sparkMaster)

    val sparkContext = new SparkContext(conf)
    val streamingContext = new StreamingContext(sparkContext, windowDuration)

    val stream = getSparkStream(streamingContext, kafkaServer, kafkaPort, Array(topic))

    val dataStore = new DataStore(mongoConnectionString)
    val mapper = new ExtraSensoryCsvMapper()
    val lines = stream.map(record => (record.key, record.value))
    val eventStream = lines.map{csv => mapper.Map(csv._2)}.window(windowDuration, slidingDuration)

    eventStream.filter(event => event != null).foreachRDD(rdd => {
      processWatchAccelerationProperties(rdd, dataStore)
      processAudioProperties(rdd, dataStore)
      processMostPopularLocationsForSitting(rdd, dataStore, numberOfMostPopularLocationsToStore, locationMatchingThreshold)
    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }

  def getSparkStream(ssc: StreamingContext, kafkaServer: String, kafkaPort: String, topics: Array[String]): InputDStream[ConsumerRecord[String, String]] = {
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> s"$kafkaServer:$kafkaPort",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "project2",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
  }

  def processWatchAccelerationProperties(rdd: RDD[mutable.Map[String, String]], dataStore: DataStore): Unit = {
    val watchAccelerationProperties = "watch_acceleration:spectrum:z_log_energy_band4"
    processAttribute(rdd, watchAccelerationProperties, dataStore)
  }

  def processAudioProperties(rdd: RDD[mutable.Map[String, String]], dataStore: DataStore): Unit = {
    val audioProperties = "audio_properties:max_abs_value"
    processAttribute(rdd, audioProperties, dataStore)
  }

  def processAttribute(rdd: RDD[mutable.Map[String, String]], label: String, dataStore: DataStore): Unit = {
    val stats = rdd.filter(r => r(label) != null && r(label) != "").map(r => r(label).toDouble).stats()
    if (stats.count == 0) {
      return
    }

    val eventStats = EventStats.apply(label, stats.min, stats.max, stats.mean, stats.count)
    eventStats.print()
    dataStore.persistEventStats(eventStats)
  }

  def processMostPopularLocationsForSitting(rdd: RDD[mutable.Map[String, String]], dataStore: DataStore, numberOfLocations: Int, threshold: Double): Unit = {
    val knownLocations = getKnownLocations()
    val sittingLabel = "label:SITTING"
    val withLocations = rdd.filter(r => r("latitude") != null && r("latitude") != "" && r("longitude") != null && r("longitude") != "" )
    val sittingEvents = withLocations.filter(r => r(sittingLabel) == "1.0")
    val eventsGroupedUnderLocations = sittingEvents.groupBy(event => {
      val sittingLocation = new Location(event("latitude").toDouble, event("longitude").toDouble)
      knownLocations.find(location => location.isCloseTo(sittingLocation, threshold))
    }).filter(group => group._1.isDefined)
    val mostPopularLocations = eventsGroupedUnderLocations
      .map(group => {
        val location = group._1.get
        val count = group._2.toList.length
        LocationStats.apply(location.latitude, location.longitude, count)
    }).takeOrdered(numberOfLocations)(Ordering[Int].on(x => -x.count))
    if (mostPopularLocations.length == 0) {
      return
    }
    val mostPopularLoc = mostPopularLocations(0)
    mostPopularLoc.print()
    dataStore.persistLocationStats(mostPopularLocations)
  }

  def getKnownLocations(): Array[Location] = {
    val locations = Array[Location](
      new Location(32.882689, -117.23468999999999),
      new Location(33.238996, -117.289125),
      new Location(33.239013, -117.288981),
      new Location(33.239077, -117.288983),
      new Location(32.882735, -117.23463100000001),
      new Location(32.882743, -117.234597),
      new Location(32.882729, -117.234606),
      new Location(32.82864, -117.154779),
      new Location(32.873143, -117.214128),
      new Location(33.239018, -117.289115)
    )
    locations
  }
}