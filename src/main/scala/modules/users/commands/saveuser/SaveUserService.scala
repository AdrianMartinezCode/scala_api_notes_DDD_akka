package modules.users.commands.saveuser

import libs.ddd.CommandHandler
import modules.users.database.UsersRepositoryPort
import modules.users.database.messages.UsersRepositoryMessages
import modules.users.database.models.UserModel

import scala.concurrent.Future
import java.util.UUID

class SaveUserService(usersRepository: UsersRepositoryPort)
  extends CommandHandler[SaveUserCommand, SaveUserCommandResponse] {

  override def handleMessage(request: SaveUserCommand): Future[SaveUserCommandResponse] = {
    val idUser = UUID.randomUUID().toString
    usersRepository.saveEntity(UserModel(request.name, idUser))
      .map(_ => SaveUserCommandResponse(idUser))
  }

}
