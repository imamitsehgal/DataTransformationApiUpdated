package com.hsbc.rsl.transformation.mapping

/**
  * Created by smit22 on 2/5/2017.
  */
case class RowSchema(rowName : String ,listOfColumns : List[ColumnSchema]) extends Serializable
