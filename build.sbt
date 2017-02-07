name := "DataTransformationApi"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
  "com.typesafe.akka" %% "akka-stream" % "2.4.4",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.4" % "test",
  "org.apache.spark" %% "spark-core" % "1.6.1",
  "com.databricks" % "spark-csv_2.11" % "1.5.0",
  "org.apache.wink" % "wink-json4j" % "1.4",
  "org.apache.spark" % "spark-sql_2.11" % "1.6.1"
)