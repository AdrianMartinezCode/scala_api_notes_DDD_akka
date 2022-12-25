package modules.users.query.getuser

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import libs.akka.{DefaultActor, GlobalActorSystem}
import modules.users.database.UsersRepository
import modules.users.database.messages.UsersRepositoryMessages
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import modules.notes.domain.Note
import modules.users.domain.User

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


class GetUserService extends DefaultActor {

  import system.dispatcher

  val repository = system.actorOf(Props[UsersRepository])

  override def receive: Receive = {
    case GetUser(idUser) =>
      (repository ? UsersRepositoryMessages.GetUser(idUser))
        .mapTo[UsersRepositoryMessages.GetUserResponse]
        .map(_.user.map{ user =>
          // TODO retrieve the notes from the notes repository as a message
          User(user.idUser, user.name, List(Note("1234", "1234", "1234")))
        })
        .map(user => GetUserResponse(user))
        .pipeTo(sender())
  }
}
