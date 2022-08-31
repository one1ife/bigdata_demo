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
import java.util.logging.Logger;

//springboot-linkto-hadoop服务类
@Service
public class HadoopService {

    // hadoop 地址
    @Value("${fs.defaultFs}")
    String nameNode;

    // 日志
    Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

    // 连接到hadoop
    public FileSystem getFileSystem() {
        try {
            //指明HDFS文件系统位置
            Configuration configuration = new Configuration();
            configuration.set("fs.defaultFs", nameNode);

            //连接HDFS文件系统
            FileSystem fileSystem = FileSystem.get(new URI(nameNode), configuration);
            return fileSystem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 创建hadoop文件夹
    public void mkdir(String path) {
        try {
            FileSystem fileSystem = getFileSystem();
            logger.info("name node connect ok!");

            //上传本地文件到hadoop
            fileSystem.mkdirs(new Path(path));

            //关闭连接
            fileSystem.close();
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    // 上传字符串数据到hadoop
    public void upload(String data, String path) {
        try {
            //指明HDFS文件系统位置
            FileSystem fileSystem = getFileSystem();
            logger.info("name node connect ok!");

            // append追加，create创建
            FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(path));
            fsDataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            fsDataOutputStream.flush();
            fsDataOutputStream.close();
            logger.info("upload string ok!");

            //关闭连接
            fileSystem.close();

        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    public void uploadFile(String file, String path) {
        try {
            FileSystem fileSystem = getFileSystem();
            fileSystem.copyFromLocalFile(new Path(file), new Path(path));
            fileSystem.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upload(InputStream inputStream, String path) {
        try {
            FileSystem fileSystem = getFileSystem();
            FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(path));
            fsDataOutputStream.write(inputStream.read());
            fileSystem.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String hdfsFile) throws IOException {
        FileSystem fileSystem = null;
        try {
            fileSystem = getFileSystem();
            FSDataInputStream fsDataInputStream = fileSystem.open(new Path(hdfsFile));
            byte byts[] = new byte[1024];
            fsDataInputStream.read(byts);
            return new String(byts);

        } catch (Exception e) {
            fileSystem.close();
            return "";
        }
    }
}
