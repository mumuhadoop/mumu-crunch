package com.lovecws.mumu.crunch.nginxlog;

import com.lovecws.mumu.crunch.CrunchConfiguration;
import com.lovecws.mumu.crunch.basic.WordCount;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: ip访问量统计
 * @date 2018-01-16 11:47:
 */
public class NginxAccessLogDateCounterTest {

    @Test
    public void NginxAccessLogDateCounter() throws Exception {

        String inputPath = CrunchConfiguration.HADOOP() + "/mumu/crunch/nginxlog/input";
        String outputPath = CrunchConfiguration.HADOOP() + "/mumu/crunch/nginxlog/outputdate" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");

        NginxAccessLogDateCounter.main(new String[]{inputPath, outputPath, "yyyy-MM"});
        CrunchConfiguration.list(outputPath);
    }
}
