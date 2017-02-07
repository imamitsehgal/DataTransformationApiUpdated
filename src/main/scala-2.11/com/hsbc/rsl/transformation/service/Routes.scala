package com.hsbc.rsl.transformation.service

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import com.hsbc.rsl.transformation.processor.Processor

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by smit22 on 1/24/2017.
  */
trait Routes {

  implicit val dispatcher: ExecutionContextExecutor

  val routes = {
    path("transformation" / IntNumber) { id =>
      get {
        complete {
          Processor.runTransformation("D:\\Data\\RSL\\DataTransformation\\PV_Data.csv","D:\\Data\\RSL\\DataTransformation\\VS_PV_Transformation.json")
          HttpResponse(entity = "Hello Siddharth")
          //HttpResponse(entity = new Processor().process("D:\\Data\\RSL\\DataTransformation\\PV_Data.csv","D:\\Data\\RSL\\DataTransformation\\PV_Data_Mapping.json")(0))
        }
      }
    }
  }


}
