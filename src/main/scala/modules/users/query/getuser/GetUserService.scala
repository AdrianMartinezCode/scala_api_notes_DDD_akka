package modules.users.query.getuser

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import libs.akka.{DefaultActor, GlobalActorSystem}
import modules.users.database.UsersRepository
import modules.users.database.messages.UsersRepositoryMessages
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import libs.ddd.ServiceHandler
import modules.notes.domain.Note
import modules.users.akka.{UsersBoundedContext, UsersBoundedContextActor}
import modules.users.domain.User

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


class GetUserService(usersBoundedContextActor: UsersBoundedContext)
  extends ServiceHandler[GetUser, GetUserResponse] {

  import system.dispatcher
  override def handleMessage(request: GetUser): Future[GetUserResponse] =
    usersBoundedContextActor
      .sendMessageToOurDatabase(UsersRepositoryMessages.GetUser(request.idUser))
      .mapTo[UsersRepositoryMessages.GetUserResponse]
      .map(_.user.map{ user =>
        // TODO retrieve the notes from the notes repository as a message
        User(user.idUser, user.name, List(Note("1234", "1234", "1234")))
      })
      .map(user => GetUserResponse(user))
}
