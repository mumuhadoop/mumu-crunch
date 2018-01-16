package com.lovecws.mumu.crunch.basic;

import org.apache.crunch.*;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.impl.mr.exec.MRExecutor;
import org.apache.crunch.impl.mr.plan.DotfileUtills;
import org.apache.crunch.io.To;
import org.apache.crunch.scrunch.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.Serializable;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: WordCount字频统计
 * @date 2018-01-15 15:53:
 */
public class WordCount extends Configured implements Tool, Serializable {

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new WordCount(), args);
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: WordCount <input path> <output path>");
            System.exit(-1);
        }
        String inputPath = args[0];
        String outputPath = args[1];

        DotfileUtills.enableDebugDotfiles(getConf());

        Pipeline pipeline = new MRPipeline(WordCount.class, getConf());

        PCollection<String> pCollection = pipeline.readTextFile(inputPath);

        pCollection = pCollection.parallelDo(new DoFn<String, String>() {
            @Override
            public void process(String s, Emitter<String> emitter) {
                String[] strings = s.split("\\s+");
                for (String str : strings) {
                    emitter.emit(str);
                }
            }
        }, Writables.strings());
        PTable<String, Long> counts = pCollection.count();

        //pipeline.writeTextFile(counts, outputPath);
        pipeline.write(counts, To.sequenceFile(outputPath));
        PipelineResult pipelineResult = pipeline.done();

        return pipelineResult.succeeded() ? 0 : 1;
    }
}
