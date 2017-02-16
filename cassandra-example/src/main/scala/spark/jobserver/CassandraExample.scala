package spark.jobserver

import com.datastax.spark.connector._
import com.typesafe.config.Config

import org.apache.spark.{SparkConf, SparkContext}
import spark.jobserver.{SparkJob, SparkJobValid, SparkJobValidation}


object CassandraExample extends SparkJob {

  override def validate(sc: SparkContext, config: Config): SparkJobValidation = {
    SparkJobValid
  }
  
  override def runJob(sc: SparkContext, config: Config): Any = {
    val collection = sc.parallelize(Seq(("key3", 3), ("key4", 4)))
    collection.saveToCassandra("test", "kv", SomeColumns("key", "value"))
  }
}
