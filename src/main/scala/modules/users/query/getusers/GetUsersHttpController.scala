package modules.users.query.getusers

import akka.actor.{ActorRef, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import libs.akka.GlobalActorSystem
import libs.api.StandardHttpController
import modules.users.akka.DefaultUsersController
import modules.users.config.UserJsonProtocol
import modules.users.query.getuser.GetUserService
import akka.pattern.ask
import spray.json._

class GetUsersHttpController(val boundedContextActor: ActorRef) extends DefaultUsersController {

  import system.dispatcher

  val route: Route = get {
    path("user") {
      complete((boundedContextActor ? GetUsers)
        .mapTo[GetUsersResponse]
        .map { users =>
          HttpResponse(
            entity = HttpEntity(
              ContentTypes.`application/json`,
              users.users.toJson.prettyPrint
            )
          )
        }
      )
    }
  }

  override def getRoute: Route = route

}
