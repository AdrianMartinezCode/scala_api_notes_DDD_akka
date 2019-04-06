package server.service

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

trait Service {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val route1 =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say Hello </h1>"))
      }
    } ~ path("ja") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say Ja </h1>"))
      }
    }
}
