package modules.notes.akka

import akka.http.scaladsl.server.Directives
import libs.akka.DefaultController
import libs.api.StandardHttpController
import libs.ddd.{Command, CommandBus}
import modules.notes.config.NoteJsonProtocol
import modules.users.config.UserJsonProtocol
import scala.reflect.runtime.universe._

abstract class DefaultNotesController[C <: Command: TypeTag, R](ch: CommandBus) extends DefaultController[C, R](ch)
  with Directives
  with NoteJsonProtocol
  with StandardHttpController {

}
