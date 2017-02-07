package com.hsbc.rsl.transformation.transformer

import org.apache.spark.sql.Row
import com.hsbc.rsl.transformation.mapping.RowSchema
import com.hsbc.rsl.transformation.transformer.RowTransformer.process

trait Transformer[T] {

  def process(inputData : org.apache.spark.sql.Row ,outputMapping : List[RowSchema]) : List[List[(String,Any)]]
  
}

object Transformer {
  
  def processRecord(inputData : org.apache.spark.sql.Row ,outputMapping : List[RowSchema]) : List[List[(String,Any)]] = inputData match {
    case x : Row => process(x.asInstanceOf[Row],outputMapping)
  }
  
}