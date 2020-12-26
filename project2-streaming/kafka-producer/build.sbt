scalaVersion := "2.12.11"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.0.0" % "provided",
  "org.apache.kafka" % "kafka_2.12" % "2.7.0" % "provided",
  "org.apache.kafka" % "kafka-clients" % "2.7.0"
)