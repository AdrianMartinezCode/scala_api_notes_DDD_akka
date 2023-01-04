package modules.users.akka

import akka.http.scaladsl.server.Directives
import libs.akka.DefaultController
import libs.api.StandardHttpController
import libs.ddd.{Command, CommandBus, CommandHandler}
import modules.users.config.UserJsonProtocol

abstract class DefaultUsersController[C <: Command, R](ch: CommandBus[_, _])
  extends DefaultController[C, R](ch)
  with Directives
  with UserJsonProtocol
  with StandardHttpController {

}
