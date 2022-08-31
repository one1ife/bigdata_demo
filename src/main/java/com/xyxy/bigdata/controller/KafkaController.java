package com.example.feng01.controller;

import com.example.feng01.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class KafkaController {

    //注入kafka服务类，提供上传下载功能
    @Autowired
    KafkaService kafkaService;

    //日志类
    Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());


    //上传前端数据到kafka
    @RequestMapping("/uploadToKafka")
    @ResponseBody
    public String uploadToHDFS(@RequestBody Map<String, String> requestMap) {

        //获取前端数据
        String name = requestMap.get("name");
        String age = requestMap.get("age");
        String sex = requestMap.get("sex");

        //生成数据
        String data = name + "," + age + "," + sex;

        //保存日志
        logger.info(data);

        //发送到kafka消息队列
        kafkaService.send("topic1", data);
        return "ok";
    }
}
