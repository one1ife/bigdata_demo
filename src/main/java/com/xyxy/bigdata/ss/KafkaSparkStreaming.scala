import org.apache.log4j
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

//kafka实时分析流
object KafkaSparkStreaming {

  //配置文件
  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "use_a_separate_group_id_for_each_stream",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  def main(args: Array[String]): Unit = {

    //接受kafka数据进行分析
    val topics = Array("topic1")

    //启动spark环境，开启2个处理线程，1个收数据，1个处理数据
    val conf = new SparkConf().setMaster("local[2]").setAppName("app")

    //创建stream流对象读取数据,每10秒接受一次数据
    val streamingContext = new StreamingContext(conf, Seconds(10))

    //开启实时分析流
    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )

    //开始分析
    stream.map(record => (record.key, record.value))
      //保存分析数据到hadoop
      .saveAsTextFiles("hdfs://127.0.0.1:9000/output")


    //开始等待数据
    stream.start();

  }


}