package com.hsbc.rsl.transformation.mapping

case class ColumnSchema(columnName:String , columnProperties : Map[String,String]) extends Serializable {

  val valueExpression : String = columnProperties.get("value") match {
    case Some(expression) => expression
    case None => throw new Exception("Blank expression can not be evaluated. Please update the mapping file.")
  }

  val dataType: Class[_ >: String with Int with Boolean with Float with Double] = columnProperties.get("type") match {
    case Some(dataTypeString) => dataTypeString.toLowerCase() match {
      case "string" => classOf[String]
      case "integer" => classOf[Int]
      case "boolean" => classOf[Boolean]
      case "float" => classOf[Float]
      case "double" => classOf[Double]
      case _ => throw new Exception("Invalid DataType specified in the mapping file.")
     }
    case None => throw new Exception("Data type can not be null in the mapping file.")
  }
  
  val isNullable : Boolean = columnProperties.get("nullable") match {
    case Some(nullableTypeInString) => nullableTypeInString.toLowerCase() match {
       case "true" => true  
       case "false" => false
       case _ => true
    }
     case None => true
  }
}
