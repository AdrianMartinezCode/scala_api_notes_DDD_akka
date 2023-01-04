package modules.users.commands.modifyuser

import akka.actor.ActorRef

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCode, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import libs.ddd.CommandBus
import modules.users.akka.DefaultUsersController
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait ModifyUserDtoJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val modifyUserDtoFormat: RootJsonFormat[ModifyUserDto] = jsonFormat1(ModifyUserDto)
}


class ModifyUserHttpController(ch: CommandBus)
  extends DefaultUsersController[ModifyUserCommand, ModifyUserCommandResponse](ch)
    with ModifyUserDtoJsonSupport {

  val route: Route = put {
    path("user" / Segment) { idUser =>
      entity(as[ModifyUserDto]) { dto => complete(sendCommand(ModifyUserCommand(idUser, dto.name))
        .map(_.result)
        .map {
          case Left(_) => HttpResponse(
            status = StatusCodes.NotFound
          )
          case Right(_) => HttpResponse(
            status = StatusCodes.OK
          )
        }
      )}
    }
  }

  override def getRoute: Route = route

}
