package com.lovecws.mumu.crunch;

import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: Crunch 配置信息测试
 * @date 2018-01-15 15:23:
 */
public class CrunchConfigurationTest {

    @Test
    public void hadoopAddress() {
        System.out.println(CrunchConfiguration.HADOOP());
    }

    @Test
    public void list() {
        CrunchConfiguration.list(CrunchConfiguration.HADOOP() + "/mumu/crunch/nginxlog/outputdate");
    }
}
