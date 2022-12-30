package modules.users.query.getuser

import akka.actor.ActorRef
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.pattern.ask
import akka.http.scaladsl.server.Route
import modules.users.akka.DefaultUsersController
import spray.json._

class GetUserHttpController(val boundedContextActor: ActorRef) extends DefaultUsersController {

  import system.dispatcher

  val route: Route = get {
    path("user" / Segment) { idu =>
        complete((boundedContextActor ? GetUser(idu))
          .mapTo[GetUserResponse]
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
