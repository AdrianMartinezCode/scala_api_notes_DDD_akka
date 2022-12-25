package di

import akka.http.scaladsl.server.Route
import libs.api.StandardHttpController
import modules.users.query.getuser.GetUserHttpController
import akka.http.scaladsl.server.Directives._
import modules.users.query.getusers.GetUsersHttpController


class AppBundle {

  private val controllers: List[StandardHttpController] = List(
    new GetUsersHttpController(),
    new GetUserHttpController()
  )

  def getRoutes() : Route = controllers.map(_.getRoute).reduceLeft(_ ~ _)

}
