package com.example.feng01.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.logging.Logger;


//hive数据仓库的上传下载工具
@Service
public class HiveService {

    @Value("${hiveAddr}")
    String hiveAddr;

    public String insert(String name,String age,String sex) throws Exception {
        //加载HIVE驱动程序
        Class.forName("org.apache.hive.jdbc.HiveDriver");

        //连接到HIVE数据库仓库
        Connection connection = DriverManager.getConnection(hiveAddr);

        //String sql=String.format("insert into student values( null,'%s','%s','%s')",name,age,sex);

        //执行SQL并查询数据
        //ResultSet resultSet=connection.createStatement().executeUpdate(sql);

        connection.close();

        return "";
    }

}