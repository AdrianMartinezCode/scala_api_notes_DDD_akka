package modules.users.query.getuser

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.pattern.ask
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import libs.akka.{DefaultController, GlobalActorSystem}
import libs.api.StandardHttpController
import modules.users.akka.DefaultUsersController
import modules.users.config.UserJsonProtocol

import scala.concurrent.duration.DurationInt
import spray.json._

class GetUserHttpController extends DefaultUsersController {

  import system.dispatcher

  val service = system.actorOf(Props[GetUserService])

  val route: Route = get {
    path("user" / Segment) { idu: String =>
        complete((service ? GetUser(idu))
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
