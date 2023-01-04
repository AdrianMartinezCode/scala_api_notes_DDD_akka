package modules.users.query.getuser

import akka.actor.{Actor, Props}
import akka.stream.ActorMaterializer
import libs.akka.{DefaultActor, GlobalActorSystem}
import modules.users.database.UsersRepository
import modules.users.database.messages.UsersRepositoryMessages
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import libs.ddd.CommandHandler
import modules.notes.domain.Note
import modules.users.domain.User

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps


class GetUserService(usersRepository: UsersRepository)
  extends CommandHandler[GetUserQuery, GetUserQueryResponse] {

  override def handleMessage(request: GetUserQuery): Future[GetUserQueryResponse] =
    usersRepository.getEntity(request.idUser)
      // TODO retrieve the notes from the notes repository as a message
      .map(_.map(m => User(m.name, m.idEntity, List())))
      .map(GetUserQueryResponse)
}
