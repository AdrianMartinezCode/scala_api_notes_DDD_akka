package modules.notes.akka

import akka.http.scaladsl.server.Directives
import libs.akka.DefaultController
import libs.api.StandardHttpController
import modules.notes.config.NoteJsonProtocol
import modules.users.config.UserJsonProtocol

abstract class DefaultNotesController extends DefaultController
  with Directives
  with NoteJsonProtocol
  with StandardHttpController {

}
