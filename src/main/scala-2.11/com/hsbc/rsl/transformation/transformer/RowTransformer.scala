package com.hsbc.rsl.transformation.transformer

import org.apache.spark.sql.Row
import com.hsbc.rsl.transformation.evaluator.ScriptEvaluator
import com.hsbc.rsl.transformation.mapping.{ColumnSchema, JsonHelper, RowSchema}


object RowTransformer extends Transformer[Row] with JsonHelper {
  
  private lazy val evaluateColumnSchema = getEvaluatedData(ScriptEvaluator.evaluate) _

  def process(inputData : org.apache.spark.sql.Row ,outputMapping : List[RowSchema]) : List[List[(String,Any)]] = {

    def processRow(rowName : String ,outputColumnMapping : List[ColumnSchema]) : List[(String,Any)] = {
      outputColumnMapping.map(z => (z.columnName,evaluateColumnSchema((rowName,z))))
    }

    ScriptEvaluator.preEvaluate(inputData.schema.fieldNames.map(x => (x,inputData.getAs[String](x))).toList)
    val outputData = outputMapping.map(x => processRow(x.rowName, x.listOfColumns))
    ScriptEvaluator.postEvaluate()
    outputData
  }


}

