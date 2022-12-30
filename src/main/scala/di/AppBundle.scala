package di

import akka.http.scaladsl.server.Route
import libs.api.StandardHttpController
import modules.users.query.getuser.GetUserHttpController
import akka.http.scaladsl.server.Directives._
import modules.users.di.UsersContextBundle
import modules.users.query.getusers.GetUsersHttpController


object AppBundle {

  private val controllers: List[StandardHttpController] =
    UsersContextBundle.getControllers ++
      List()

  def getRoutes : Route = controllers.map(_.getRoute).reduceLeft(_ ~ _)

}
