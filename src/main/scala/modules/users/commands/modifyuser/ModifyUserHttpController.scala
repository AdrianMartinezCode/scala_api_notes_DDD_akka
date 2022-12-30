package modules.users.commands.modifyuser

import akka.actor.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCode, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import modules.users.akka.DefaultUsersController
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait ModifyUserDtoJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val modifyUserDtoFormat: RootJsonFormat[ModifyUserDto] = jsonFormat1(ModifyUserDto)
}

class ModifyUserHttpController(val boundedContextActor: ActorRef)
  extends DefaultUsersController
    with ModifyUserDtoJsonSupport {

  import system.dispatcher

  val route: Route = put {
    path("user" / Segment) { idUser =>
      entity(as[ModifyUserDto]) { dto => {
        complete((boundedContextActor ? ModifyUserCommand(idUser, dto.name))
          .mapTo[ModifyUserCommandResponse]
          .map {
            case Left(_) => HttpResponse(
              status = StatusCodes.NotFound
            )
            case Right(_) => HttpResponse(
              status = StatusCodes.OK
            )
          }
        )
      }}
    }
  }

  override def getRoute: Route = route
}
