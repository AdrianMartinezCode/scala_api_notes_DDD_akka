package server.service

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.Materializer
import data.repositories.UsersRepository
import domain.{Note, User}
import server.service.AkkaHttpMicroservice.usersRepo
import spray.json._

import scala.concurrent.{ExecutionContextExecutor, Future}


trait Service extends Directives with JsonSupport {
  implicit val system: ActorSystem
  implicit val executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val usersRepo: UsersRepository.type = UsersRepository

  def initService() : Future[Done] = {
    usersRepo.initBd()
  }


  val routeUsers: Route =
    get {
      path("notes") {
        complete(usersRepo.getAllNotes)
      }
    } ~ get {
      path("users") {
        complete(usersRepo.getAllUsers)
      }
    }

  /*val route1 =
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say Hello </h1>"))
      }*/
    } /*:: path("ja") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say Ja </h1>"))
      }
    }*/
}
