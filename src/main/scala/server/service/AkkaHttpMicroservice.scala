package server.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import di.AppBundle
import libs.akka.GlobalActorSystem

import scala.io.StdIn

object AkkaHttpMicroservice extends App {

  // From trait Service
  implicit val system = GlobalActorSystem.system
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  private val bindingFuture = Http().bindAndHandle(AppBundle.getRoutes, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress return to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
