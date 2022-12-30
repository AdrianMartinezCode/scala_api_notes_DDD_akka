package modules.notes.database

import akka.actor.{Actor, ActorLogging}
import libs.akka.GlobalActorSystem
import modules.notes.database.messages.NotesRepositoryMessages

class NotesRepository extends Actor with ActorLogging {

  implicit val system = GlobalActorSystem.system
  implicit val executionContext = system.dispatcher

  import NotesRepositoryMessages._



  override def receive: Receive = ???
}
