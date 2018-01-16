package com.lovecws.mumu.crunch.basic;

import com.lovecws.mumu.crunch.CrunchConfiguration;
import org.junit.Test;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 字数统计测试
 * @date 2018-01-15 17:04:
 */
public class WordCountTest {

    @Test
    public void wordcount() throws Exception {
        WordCount.main(new String[]{CrunchConfiguration.HADOOP() + "/mumu/crunch/wordcount/input",
                CrunchConfiguration.HADOOP() + "/mumu/crunch/wordcount/outputseq"});
    }
}
