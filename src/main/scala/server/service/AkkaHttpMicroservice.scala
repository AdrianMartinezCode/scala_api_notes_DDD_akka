package server.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import data.UsersRepository

import scala.io.StdIn

object AkkaHttpMicroservice extends App with Service {

  // From trait Service
  override implicit val system = ActorSystem("my-system")
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  initService
    .onComplete(_ => {
      val bindingFuture = Http().bindAndHandle(routeUsers, "localhost", 8080)
      println(s"Server online at http://localhost:8080/\nPress return to stop...")
      StdIn.readLine()
      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    })




}
