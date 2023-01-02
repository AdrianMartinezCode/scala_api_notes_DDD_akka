package modules.users.query.getusers

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import akka.util.Timeout
import libs.akka.{DefaultActor, GlobalActorSystem}
import modules.users.database.UsersRepository
import akka.pattern.{ask, pipe}
import libs.database.DatabasePort
import libs.ddd.ServiceHandler
import modules.notes.domain.Note
import modules.users.akka.UsersBoundedContext
import modules.users.database.messages.UsersRepositoryMessages
import modules.users.database.models.UserModel
import modules.users.domain.User
import modules.users.query.getuser.{GetUser, GetUserResponse}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class GetUsersService(usersRepository: UsersRepository)
  extends ServiceHandler[GetUsers, GetUsersResponse] {


  override def handleMessage(request: GetUsers): Future[GetUsersResponse] = {
    usersRepository.
//    usersBoundedContextActor
//      .sendMessageToOurDatabase(UsersRepositoryMessages.GetUsers)
//      .mapTo[UsersRepositoryMessages.GetUsersResponse]
//      .map(_.users.map{ user =>
//        // TODO retrieve the notes from the notes repository as a message
//        User(user.idUser, user.name, List(Note("1234", "1234", "1234")))
//      })
//      .map(users => GetUsersResponse(users))
  }
}
