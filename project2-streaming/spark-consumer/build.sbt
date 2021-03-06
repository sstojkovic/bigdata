scalaVersion := "2.12.11"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.0.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.0.1" % "provided"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.1.1"

assemblyMergeStrategy in assembly:= {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (assemblyMergeStrategy in assembly).value(x)
}