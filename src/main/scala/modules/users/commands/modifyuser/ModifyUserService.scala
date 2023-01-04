package modules.users.commands.modifyuser

import libs.ddd.{CommandHandler, EntityNotFoundException}
import modules.users.database.UsersRepositoryPort
import modules.users.database.models.UserModel
import modules.users.domain.User

import scala.concurrent.Future

class ModifyUserService(usersRepository: UsersRepositoryPort)
  extends CommandHandler[ModifyUserCommand, ModifyUserCommandResponse] {

  override def handleMessage(request: ModifyUserCommand): Future[ModifyUserCommandResponse] =
    usersRepository.getEntity(request.idUser)
      .flatMap{
        case None => Future.successful(ModifyUserCommandResponse(new EntityNotFoundException()))
        case Some(_) => request.name
            .map(UserModel(_, request.idUser))
            .map(usersRepository.saveEntity)
            // TODO retrieve the notes from the notes repository as a message
            .map(_.map(_.map(model => User(model.name, model.idEntity, List()))))
            .map(_.map(user => ModifyUserCommandResponse(user)))
            .getOrElse(Future.successful(ModifyUserCommandResponse(new Exception("Name not specified"))))
      }

}
