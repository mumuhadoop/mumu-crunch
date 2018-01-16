package com.lovecws.mumu.crunch.nginxlog;

import com.lovecws.mumu.crunch.CrunchConfiguration;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: ip访问量统计
 * @date 2018-01-16 11:47:
 */
public class NginxAccessLogIpCounterTest {

    @Test
    public void NginxAccessLogIpCounter() throws Exception {

        String inputPath = CrunchConfiguration.HADOOP() + "/mumu/crunch/nginxlog/input";
        String outputPath = CrunchConfiguration.HADOOP() + "/mumu/crunch/nginxlog/outputip";

        NginxAccessLogIpCounter.main(new String[]{inputPath, outputPath});
        CrunchConfiguration.list(outputPath);
    }
}
