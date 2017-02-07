package com.hsbc.rsl.transformation.processor

import com.hsbc.rsl.transformation.common.TransformationHelper
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.Row
import com.hsbc.rsl.transformation.mapping.{ RowSchema, TransformationMapping}
import com.hsbc.rsl.transformation.transformer.Transformer
/**
  * Created by smit22 on 1/24/2017.
  */
class Processor extends BaseProcessor with TransformationHelper {

  def process(rawDataFile : String , transformationMappingFileName : String )
  {
    val inputRawDataDF = Processor.sqlContext.load("com.databricks.spark.csv", Map("path" -> rawDataFile, "header" -> "true"))

    val outputDataMapping : List[RowSchema] =  TransformationMapping.fetchTransformationMapping(transformationMappingFileName)

    val outputRowRdd = inputRawDataDF.rdd.map(x => Transformer.processRecord(x,outputDataMapping)).flatMap(x => x).map(x => x.map(a => a._2))
      .map(z => Row.fromSeq(z))

    val outputDataframe = Processor.sqlContext.createDataFrame(outputRowRdd, getSchema(outputDataMapping.head.listOfColumns))

    outputDataframe.registerTempTable("PV_RISK_DATA")

    outputDataframe.printSchema()
    Processor.sqlContext.sql("select * from PV_RISK_DATA").show()

    //outputDataframe.rdd.map(x => x.toString()).collect().toList

    //x.foreach(println)
    //outputDataframe.write.format("com.databricks.spark.csv").option("header","true")save("file:///D:\\output\\out.csv")
  }
}


object Processor {

  private val conf = new SparkConf().setMaster("local").setAppName("DataTransformation")
  private val sc = new SparkContext(conf)
  private val sqlContext = new org.apache.spark.sql.SQLContext(sc)

  def runTransformation(sourceFile : String, mappingFile: String) = {
    new Processor().process(sourceFile,mappingFile)
  }

  def main(args: Array[String]) : Unit = {
    args match {
      case x if x.length == 2 => new Processor().process(x(0), x(1))
      case _ => throw new Exception("Invalid argument, Please provide [raw data file name] and [tranformation mapping file name].")
    }
  }
}
