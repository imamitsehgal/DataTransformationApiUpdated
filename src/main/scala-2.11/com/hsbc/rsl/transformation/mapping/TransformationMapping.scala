package com.hsbc.rsl.transformation.mapping

import scala.io.Source

sealed trait TransformationMapping {

  val asJsonString: (String) => String = (fileName : String) => Source.fromFile(fileName).getLines.mkString
  def getMapping(outputMappingFileName : String) : List[RowSchema]
}

object TransformationMapping {
  def fetchTransformationMapping(fileName : String) : List[RowSchema] = fileName match {
    case x if JsonMapping.isJsonMapping(x)  => JsonMapping getMapping x
    case _ => throw new Exception("Invalid mapping file type , must be of type JSON.")
  }
}


object JsonMapping extends TransformationMapping with JsonHelper {
  
 val isJsonMapping: (String) => Boolean = (fileName : String ) =>  fileName.toLowerCase().endsWith(".json")
 
 override def getMapping(mappingFileName : String): List[RowSchema] = parse(asJsonString(mappingFileName))
}
