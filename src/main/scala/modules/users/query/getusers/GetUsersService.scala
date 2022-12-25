package modules.users.query.getusers

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import libs.akka.{DefaultActor, GlobalActorSystem}
import modules.users.database.UsersRepository
import akka.pattern.{ask, pipe}
import modules.notes.domain.Note
import modules.users.database.messages.UsersRepositoryMessages
import modules.users.domain.User

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class GetUsersService extends DefaultActor {
  import system.dispatcher

  val repository = system.actorOf(Props[UsersRepository])

  override def receive: Receive = {
    case GetUsers =>
      (repository ? UsersRepositoryMessages.GetUsers)
      .mapTo[UsersRepositoryMessages.GetUsersResponse]
      .map(_.users.map{ user =>
        // TODO retrieve the notes from the notes repository as a message
        User(user.idUser, user.name, List(Note("1234", "1234", "1234")))
      })
      .map(users => GetUsersResponse(users))
      .pipeTo(sender())
  }
}
