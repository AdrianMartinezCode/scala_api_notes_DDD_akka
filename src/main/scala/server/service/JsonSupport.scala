package server.service

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import domain.{Note, User}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val noteFormat: RootJsonFormat[Note] = jsonFormat3(Note) // jsonFormat3 because Note has 3 attributes
  implicit val userFormat: RootJsonFormat[User] = jsonFormat3(User) // same case
}
