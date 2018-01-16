package com.lovecws.mumu.crunch.nginxlog;

import com.lovecws.mumu.crunch.basic.WordCount;
import org.apache.crunch.*;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.impl.mr.plan.DotfileUtills;
import org.apache.crunch.io.To;
import org.apache.crunch.scrunch.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.Serializable;
import java.util.Map;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: nginxlog 日志统计
 * @date 2018-01-16 11:39:
 */
public class NginxAccessLogIpCounter extends Configured implements Tool, Serializable {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new NginxAccessLogIpCounter(), args);
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: NginxAccessLogIpCounter <input path> <output path>");
            System.exit(-1);
        }
        String inputPath = args[0];
        String outputPath = args[1];

        DotfileUtills.enableDebugDotfiles(getConf());

        Pipeline pipeline = new MRPipeline(WordCount.class, getConf());

        PCollection<String> pCollection = pipeline.readTextFile(inputPath);

        pCollection = pCollection.parallelDo(new DoFn<String, String>() {
            @Override
            public void process(String line, Emitter<String> emitter) {
                Map<String, Object> accessLogMap = NginxAccessLogParser.parseLine(line);
                emitter.emit(accessLogMap.get("remoteAddr").toString());
            }
        }, Writables.strings());
        //获取前十条数据
        PTable<String, Long> counts = pCollection.count().top(10);
        pipeline.write(counts, To.textFile(outputPath));
        PipelineResult pipelineResult = pipeline.done();

        return pipelineResult.succeeded() ? 0 : 1;
    }
}
