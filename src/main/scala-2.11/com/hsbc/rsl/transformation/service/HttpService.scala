package com.hsbc.rsl.transformation.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by smit22 on 1/24/2017.
  */
object HttpService extends App with Routes {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  Http().bindAndHandle(routes, "localhost", 9000)
}
