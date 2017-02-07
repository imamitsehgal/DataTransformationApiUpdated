package com.hsbc.rsl.transformation.mapping

import org.apache.wink.json4j.OrderedJSONObject

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

trait JsonHelper {

  def getEvaluatedData(fn : ((String,String) => String))( schemaTuple : (String,ColumnSchema)) : Any = schemaTuple._2.dataType match {
      case x if x == classOf[String] => getValidString(fn(schemaTuple._1 + "_" + schemaTuple._2.columnName,schemaTuple._2.valueExpression))
      case x if x == classOf[Int] => getValidInt(fn(schemaTuple._1 + "_" + schemaTuple._2.columnName,schemaTuple._2.valueExpression))
      case x if x == classOf[Boolean] => getValidBoolean(fn(schemaTuple._1 + "_" + schemaTuple._2.columnName,schemaTuple._2.valueExpression))
      case x if x == classOf[Float] => getValidFloat(fn(schemaTuple._1 + "_" + schemaTuple._2.columnName,schemaTuple._2.valueExpression))
      case x if x == classOf[Double] => getValidDouble(fn(schemaTuple._1 + "_" + schemaTuple._2.columnName,schemaTuple._2.valueExpression))
  }

  def parse(jsonString : String) : List[RowSchema] = {
    val rowJsonObjectList = new OrderedJSONObject(jsonString,true)
    val rowIter = rowJsonObjectList.getOrder
    var listOfRows : ListBuffer[RowSchema] = new ListBuffer[RowSchema]()
    while(rowIter.hasNext)
    {
      val rowName = rowIter.next().toString
      val columnJson = rowJsonObjectList.getJSONObject(rowName)
      val columnOrderedList = new OrderedJSONObject(columnJson.toString(),true)
      val columnIter = columnOrderedList.getOrder
      var listOfColumns : ListBuffer[ColumnSchema] = new ListBuffer[ColumnSchema]()
      while(columnIter.hasNext)
      {
        val columnName = columnIter.next().toString
        val propertiesJson = columnOrderedList.getJSONObject(columnName)
        val propertiesOrderedList = new OrderedJSONObject(propertiesJson.toString(),true)
        val propertiesOrderIter = propertiesOrderedList.getOrder
        val propertiesMap = new mutable.HashMap[String,String]()
        while(propertiesOrderIter.hasNext)
        {
          val property = propertiesOrderIter.next().toString
          val propertyValue = propertiesOrderedList.getString(property)
          propertiesMap.put(property, propertyValue)
        }
        listOfColumns =listOfColumns.:+(ColumnSchema(columnName,propertiesMap.toMap))
      }
      listOfRows = listOfRows.:+(RowSchema(rowName,listOfColumns.toList))
    }
    listOfRows.toList
  }

  def getValidString(self : String) : Any = Option(self) match {
    case Some(expValue) if expValue.toString.trim.isEmpty => null
    case Some(expValue) if !expValue.toString.trim.isEmpty => expValue.toString
    case None => null
  }

  def getValidInt(self : String) : Any = Option(self) match {
    case Some(expValue) if expValue.toString.trim.isEmpty => null
    case Some(expValue) if !expValue.toString.trim.isEmpty => expValue.toInt
    case None => null
  }

  def getValidFloat(self : String) : Any = Option(self) match {
    case Some(expValue) if expValue.toString.trim.isEmpty => null
    case Some(expValue) if !expValue.toString.trim.isEmpty => expValue.toFloat
    case None => null
  }

  def getValidDouble(self : String) : Any = Option(self) match {
    case Some(expValue) if expValue.toString.trim.isEmpty => null
    case Some(expValue) if !expValue.toString.trim.isEmpty => expValue.toDouble
    case None => null
  }

  def getValidBoolean(self : String) : Any = Option(self) match {
    case Some(expValue) if expValue.toString.trim.isEmpty => null
    case Some(expValue) if !expValue.toString.trim.isEmpty => expValue.toBoolean
    case None => null
  }

}