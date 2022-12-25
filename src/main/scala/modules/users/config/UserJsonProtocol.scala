package modules.users.config

import modules.users.domain.User
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import modules.notes.domain.Note
import spray.json._
import spray.json.DefaultJsonProtocol._
import modules.notes.config.NoteJsonProtocol

trait UserJsonProtocol extends DefaultJsonProtocol with NoteJsonProtocol {
  implicit val userFormat = jsonFormat3(User)
//  implicit val userFormat : RootJsonFormat[User] = new RootJsonFormat[User] {
//    override def read(json: JsValue): User = {
//      val name = json.asJsObject.getFields("name").head.convertTo[String]
//      val idUser = json.asJsObject.getFields("idUser").head.convertTo[String]
//      val notes = json.asJsObject.getFields("notes").head.convertTo[List[Note]]
//      User(name, idUser, notes)
//    }
//
//    override def write(user: User): JsValue = {
//      val fields = List(
//        "name" -> JsString(user.name),
//        "idUser" -> JsString(user.idUser),
//        "notes" -> user.notes.toJson
//      )
//      JsObject(fields)
//    }
//  }
}
