package modules.users.query.getuser

import akka.actor.ActorRef
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.pattern.ask
import akka.http.scaladsl.server.Route
import libs.ddd.CommandBus
import modules.users.akka.DefaultUsersController
import spray.json._

class GetUserHttpController(ch: CommandBus[_, _])
  extends DefaultUsersController[GetUserQuery, GetUserQueryResponse](ch) {

  val route: Route = get {
    path("user" / Segment) { idu =>
        complete(commandBus.execute(GetUserQuery(idu))
          .map { opt =>
            opt.userOption match {
              case None => HttpResponse(status = StatusCodes.NotFound)
              case Some(user) =>
                HttpResponse(
                  entity = HttpEntity(
                    ContentTypes.`application/json`,
                    user.toJson.prettyPrint
                  )
                )
            }
          }
        )
    }
  }

  override def getRoute: Route = route
}
