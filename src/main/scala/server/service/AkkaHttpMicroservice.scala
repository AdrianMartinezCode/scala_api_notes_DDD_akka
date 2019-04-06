package server.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object AkkaHttpMicroservice extends App with Service {

  // From trait Service
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()


  val bindingFuture = Http().bindAndHandle(route1, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress return to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}
