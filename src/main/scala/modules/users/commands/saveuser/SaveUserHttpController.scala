package modules.users.commands.saveuser

import akka.actor.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import modules.users.akka.DefaultUsersController
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait SaveUserDtoJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val saveUserDtoFormat: RootJsonFormat[SaveUserDto] = jsonFormat1(SaveUserDto)
}

class SaveUserHttpController(val boundedContextActor: ActorRef)
  extends DefaultUsersController
    with SaveUserDtoJsonSupport {

  import system.dispatcher

  val route: Route = post {
    path("user") {
      entity(as[SaveUserDto]) { dto =>
        complete((boundedContextActor ? SaveUserCommand(dto.name))
          .mapTo[SaveUserCommandResponse]
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
