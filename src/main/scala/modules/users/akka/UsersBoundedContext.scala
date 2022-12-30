package modules.users.akka

import scala.concurrent.Future

trait UsersBoundedContext {
  def sendMessageToOurDatabase[Q, A] (question: Q): Future[A]
  def sendMessageToNotesBoundedContext[Q, A](question: Q): Future[A]
}
