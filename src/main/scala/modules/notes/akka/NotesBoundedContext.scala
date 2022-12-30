package modules.notes.akka

import scala.concurrent.Future

trait NotesBoundedContext {
  def sendMessageToOurDatabase[Q, A] (question: Q): Future[A]

}
