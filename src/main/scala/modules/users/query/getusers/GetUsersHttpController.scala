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
import libs.ddd.CommandBus
import spray.json._

class GetUsersHttpController(ch: CommandBus[_, _])
  extends DefaultUsersController[GetUsersQuery, GetUsersQueryResponse](ch) {

  val route: Route = get {
    path("user") {
      complete(commandBus.execute(GetUsersQuery())
        .map(users =>
          HttpResponse(
            entity = HttpEntity(
              ContentTypes.`application/json`,
              users.users.toJson.prettyPrint
            )
          )
        )
      )
    }
  }

  override def getRoute: Route = route

}
