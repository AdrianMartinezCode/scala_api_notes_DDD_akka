package modules.users.akka

import akka.http.scaladsl.server.Directives
import libs.akka.DefaultController
import libs.api.StandardHttpController
import libs.ddd.{Command, CommandBus, CommandHandler}
import modules.users.config.UserJsonProtocol
import scala.reflect.runtime.universe._

abstract class DefaultUsersController[C <: Command: TypeTag, R](ch: CommandBus)
  extends DefaultController[C, R](ch)
  with Directives
  with UserJsonProtocol
  with StandardHttpController {

}
