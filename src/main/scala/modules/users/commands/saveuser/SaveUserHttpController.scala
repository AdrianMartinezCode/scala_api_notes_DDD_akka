package modules.users.commands.saveuser

import akka.actor.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import libs.ddd.CommandBus
import modules.users.akka.DefaultUsersController
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait SaveUserDtoJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val saveUserDtoFormat: RootJsonFormat[SaveUserDto] = jsonFormat1(SaveUserDto)
}

class SaveUserHttpController(ch: CommandBus)
  extends DefaultUsersController[SaveUserCommand, SaveUserCommandResponse](ch)
    with SaveUserDtoJsonSupport {

  val route: Route = post {
    path("user") {
      entity(as[SaveUserDto]) { dto =>
        complete(sendCommand(SaveUserCommand(dto.name))
          .map { response =>
            HttpResponse(
              entity = HttpEntity(
                ContentTypes.`application/json`,
                s"{'idUser': '${response.idUser}'}"
              )
            )
          }
        )
      }
    }
  }

  override def getRoute: Route = route

}
