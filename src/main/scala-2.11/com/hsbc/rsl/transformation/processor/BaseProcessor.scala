package com.hsbc.rsl.transformation.processor

/**
  * Created by smit22 on 1/24/2017.
  */
abstract class BaseProcessor {
  def process(rawDataFile : String , transformationMappingFileName : String )
}
