package com.example.feng01.controller;

import com.example.feng01.service.HadoopService;
import org.springframework.stereotype.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

//hadoop控制器
@Controller
public class HadoopController {

    //注入hadoop服务类，提供上传下载功能
    @Autowired
    HadoopService hadoopService;

    //日志类
    Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());


    //上传前端数据到hadoop
    @RequestMapping("/uploadToHDFS")
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

        //保存到hadoop
        hadoopService.upload(data, "/input/input.txt" + System.currentTimeMillis());
        return "ok";
    }


    //下载hadoop数据到前端
    @RequestMapping("/getFromHdfs")
    @ResponseBody
    public String getFromHdfs(HttpServletRequest request) throws IOException {
        String data = hadoopService.get("/output");
        return data;
    }


}
