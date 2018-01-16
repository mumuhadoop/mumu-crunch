## mumu-crunch mapreduce pipeline工具包

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumuhadoop/mumu-crunch/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/mumuhadoop/mumu-crunch.svg?branch=master)](https://travis-ci.org/mumuhadoop/mumu-crunch)
[![codecov](https://codecov.io/gh/mumuhadoop/mumu-crunch/branch/master/graph/badge.svg)](https://codecov.io/gh/mumuhadoop/mumu-crunch)

mumu-crunch是一个使用apache crunch api来运行mapreduce的java工具包，crunch大大减少了mapreduce的开发难度，不用编写mapper、reduce函数，而是通过pipeline来运行mapreduce程序。


## crunch描述
Running on top of Hadoop MapReduce and Apache Spark, the Apache Crunch™ library is a simple Java API for tasks like joining and data aggregation that are tedious to implement on plain MapReduce. The APIs are especially useful when processing data that does not fit naturally into relational model, such as time series, serialized object formats like protocol buffers or Avro records, and HBase rows and columns. For Scala users, there is the Scrunch API, which is built on top of the Java APIs and includes a REPL (read-eval-print loop) for creating MapReduce pipelines.

## mapreduce框架比较

Concept                      | Apache Hadoop MapReduce      | Apache Crunch   | Apache Pig            | Apache Spark                     | Cascading                   | Apache Hive                       | Apache Tez
---                          |---                           | ---             | ---                   | ---                              | ---                         | ---                               |  ---      
Input Data                   |InputFormat                   | Source          | LoadFunc              | InputFormat                      | Tap (Source)                | SerDe                             | Tez Input
Output Data                  |OutputFormat                  | Target          | StoreFunc             | OutputFormat                     | Tap (Sink)                  | SerDe                             | Tez Output
Data Container Abstraction   |N/A                           |PCollection      | Relation              | RDD                              | Pipe                        | Table                             | Vertex
Data Format and Serialization|Writables                     | POJOs and PTypes| Pig Tuples and Schemas| POJOs and Java/Kryo Serialization| Cascading Tuples and Schemes| List<Object> and ObjectInspectors | Events       
Data Transformation          |Mapper, Reducer, and Combiner | DoFn            | PigLatin and UDFs     | Functions (Java API)             | Operations                  | HiveQL and UDFs                   | Processor 


## 开发案列
```
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

```
## 相关阅读

[hadoop官网文档](http://hadoop.apache.org)

[Apache Crunch 官网](http://crunch.apache.org/)

## 联系方式

以上观点纯属个人看法，如有不同，欢迎指正。

email:<babymm@aliyun.com>

github:[https://github.com/babymm](https://github.com/babymm)