package com.example.feng01.service;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

//消息队列kafka服务类：提供给kafka发送消息功能
@Service
public class KafkaService {
    @Value("${kafkaAddr}")
    String kafkaAddr;

    //发送数据到kafka指定主题
    public String send(String topic, String record) {

        Properties p = new Properties();

        //kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "1kafkaAddr");
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(p);
        ProducerRecord<String, String> record1 = new ProducerRecord<String, String>(topic, record);
        kafkaProducer.send(record1);
        System.out.println("消息发送成功:" + record);

        kafkaProducer.close();

        return "ok";

    }
}
