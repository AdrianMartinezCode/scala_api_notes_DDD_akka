package modules.users.config

import modules.users.domain.User
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import modules.notes.domain.Note
import spray.json._
import spray.json.DefaultJsonProtocol._
import modules.notes.config.NoteJsonProtocol

trait UserJsonProtocol extends DefaultJsonProtocol with NoteJsonProtocol {
  implicit val userFormat = jsonFormat3(User)
}
