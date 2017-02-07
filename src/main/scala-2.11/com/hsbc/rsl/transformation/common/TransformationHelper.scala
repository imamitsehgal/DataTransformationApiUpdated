package com.hsbc.rsl.transformation.common

import org.apache.spark.sql.types._
import com.hsbc.rsl.transformation.mapping.ColumnSchema
import java.util.Date

/**
  * Created by smit22 on 1/24/2017.
  */
trait TransformationHelper {
  def getSchema( outputDataMapping : List[ColumnSchema]) : StructType = StructType(outputDataMapping.map(x => StructField(x.columnName, getStructFieldType(x.dataType),x.isNullable)))

  private def getStructFieldType(dataType : Any) : DataType = dataType match {
    case x if x == classOf[String]  => StringType
    case x if x == classOf[Int] => IntegerType
    case x if x == classOf[Boolean] => BooleanType
    case x if x == classOf[Double] => DoubleType
    case x if x == classOf[Float] => FloatType
    case x if x == classOf[Date] => DateType
    case _ => throw new Exception("Invalid Data type specified in mapping File.")
  }

}
