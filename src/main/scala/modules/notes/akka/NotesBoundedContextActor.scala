package modules.notes.akka

import akka.actor.Props
import akka.pattern.ask
import libs.akka.DefaultActor
import modules.notes.database.NotesRepository
import modules.users.akka.UsersBoundedContext

import scala.concurrent.Future

class NotesBoundedContextActor extends DefaultActor with NotesBoundedContext {

  private val repository = system.actorOf(Props[NotesRepository])

  override def sendMessageToOurDatabase[Q, A](question: Q): Future[A] =
    (repository ? question).mapTo[A]

  override def receive: Receive = ???
}
