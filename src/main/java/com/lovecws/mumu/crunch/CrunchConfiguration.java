package com.lovecws.mumu.crunch;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: Crunch 配置信息
 * @date 2018-01-15 15:23:
 */
public class CrunchConfiguration {

    private static final Logger log = Logger.getLogger(CrunchConfiguration.class);

    public static final String DEFAULT_HADOOP_ADDRESS = "hdfs://192.168.11.25:9000";

    public static String HADOOP() {
        String default_hadoop_address = System.getenv("DEFAULT_HADOOP_ADDRESS");
        if (default_hadoop_address == null) {
            default_hadoop_address = DEFAULT_HADOOP_ADDRESS;
        }
        return default_hadoop_address;
    }

    public static void list(String path) {
        DistributedFileSystem distributedFileSystem = new DistributedFileSystem();
        try {
            distributedFileSystem.initialize(new URI(HADOOP()), new Configuration());
            FileStatus[] fileStatuses = distributedFileSystem.listStatus(new Path(path));
            for (FileStatus fileStatus : fileStatuses) {
                log.info(fileStatus);
                if (fileStatus.isFile()) {
                    FSDataInputStream fsDataInputStream = distributedFileSystem.open(fileStatus.getPath());
                    byte[] bs = new byte[fsDataInputStream.available()];
                    fsDataInputStream.read(bs);
                    log.info(new String(bs));
                    fsDataInputStream.close();
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
