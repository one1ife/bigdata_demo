package com.example.feng01.ss
import org.apache.log4j
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, sql, streaming}

object HiveSparkStreaming {

  def main(args: Array[String]): Unit = {

    //开启日志
    Logger.getLogger("org").setLevel(Level.ERROR)

    //启动spark环境，开启2个处理线程，1个收数据，1个处理数据
    val conf = new SparkConf().setMaster("local[2]").setAppName("app")

    //创建dstream流对象读取数据,每10秒接受一次数据
    val stream = new StreamingContext(conf, Seconds(10))

    //对stream处理
    stream
      //读取目录
      .textFileStream("hdfs://127.0.0.1:9000/hive/student")
      //rdd
      //输出hadoop
      .foreachRDD(rdd => {
        rdd.saveAsTextFile("hdfs://127.0.0.1:9000/output")
      })

    //开始
    stream.start()

    //等待
    stream.awaitTermination()


  }

}
