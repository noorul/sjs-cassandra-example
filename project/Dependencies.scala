import sbt._

object Dependencies {

  import Versions._

  //NettyIo is very bad one, the organization name is different from the jar name for older versions
  val excludeNettyIo = ExclusionRule(organization = "org.jboss.netty")
  val excludeJline = ExclusionRule(organization = "jline")
  val excludeSlf4j = ExclusionRule(organization = "org.slf4j")
  val excludeXerial = ExclusionRule(organization = "org.xerial.snappy")
  val excludeJpountz = ExclusionRule(organization = "net.jpountz.lz4")
  val excludeScalaLang = ExclusionRule(organization = "org.scala-lang")
  val excludeGuava = ExclusionRule(organization = "com.google.guava")
  val excludeScalaTest = ExclusionRule(organization = "org.scalatest")
  val excludeSparkTags = ExclusionRule(organization = "org.apache.spark", name = "spark-tags_2.11")
  val excludeSparkProject = ExclusionRule(organization = "org.spark-project.spark")

  lazy val sparkDeps = Seq(
    "org.apache.spark" %% "spark-core" % spark % "provided" excludeAll (excludeNettyIo),
    // Force netty version.  This avoids some Spark netty dependency problem.
    "io.netty" % "netty-all" % netty
  )

  lazy val sparkExtraDeps = Seq(
    "org.apache.spark" %% "spark-sql" % spark % "provided" excludeAll (excludeNettyIo),
    "org.apache.spark" %% "spark-streaming" % spark % "provided" excludeAll (excludeNettyIo),
    "org.apache.spark" %% "spark-streaming-kafka-0-8" % spark excludeAll(excludeXerial, excludeNettyIo,
      excludeJline, excludeJpountz, excludeSparkProject, excludeScalaTest, excludeSparkTags)
  )

  lazy val jobserverDeps = Seq(
    "com.github.spark-jobserver.spark-jobserver" % "job-server-api_2.11" % jobServer % "provided",
    "com.github.spark-jobserver.spark-jobserver" % "job-server-extras_2.11" % jobServer % "provided"
  )

  lazy val cassandraConnectorDeps = Seq(
    "com.datastax.spark" %% "spark-cassandra-connector" % cassandraConnector excludeAll(excludeScalaLang, excludeNettyIo,
      excludeSparkProject)
  )
  
  lazy val otherDeps = Seq(
    "com.google.protobuf" % "protobuf-java" % protobuf,
    "com.github.nscala-time" %% "nscala-time" % nscala,
    "org.slf4j" % "slf4j-api" % slf4j % "provided"
  )

  // This is needed or else some dependency will resolve to 1.3.1 which is in jdk-8
  lazy val typeSafeConfigDeps = Seq("com.typesafe" % "config" % typesafeConfig force())

  lazy val coreTestDeps = Seq()

  val repos = Seq(
    "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "Job Server Bintray" at "https://dl.bintray.com/spark-jobserver/maven",
    "jitpack" at "https://jitpack.io"
  )
}
