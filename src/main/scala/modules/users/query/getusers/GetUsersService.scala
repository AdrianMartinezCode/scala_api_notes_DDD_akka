package modules.users.query.getusers

import modules.users.database.UsersRepository
import libs.ddd.CommandHandler
import modules.users.domain.User

import scala.concurrent.Future

class GetUsersService(usersRepository: UsersRepository)
  extends CommandHandler[GetUsersQuery, GetUsersQueryResponse] {


  override def handleMessage(request: GetUsersQuery): Future[GetUsersQueryResponse] = {
    usersRepository.getAllEntities
      // TODO retrieve the notes from the notes repository as a message
      .map(_.map(m => User(m.name, m.idEntity, List())))
      .map(GetUsersQueryResponse)
  }
}
