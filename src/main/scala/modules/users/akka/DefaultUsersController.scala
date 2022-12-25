package modules.users.akka

import akka.http.scaladsl.server.Directives
import libs.akka.DefaultController
import libs.api.StandardHttpController
import modules.users.config.UserJsonProtocol

abstract class DefaultUsersController extends DefaultController
  with Directives
  with UserJsonProtocol
  with StandardHttpController {

}
