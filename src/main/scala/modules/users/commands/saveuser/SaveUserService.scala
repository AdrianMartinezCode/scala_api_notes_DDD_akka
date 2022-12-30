package modules.users.commands.saveuser

import libs.ddd.ServiceHandler
import modules.users.akka.UsersBoundedContextActor
import modules.users.database.messages.UsersRepositoryMessages
import modules.users.database.models.UserModel

import scala.concurrent.Future
import java.util.UUID

class SaveUserService(usersBoundedContextActor: UsersBoundedContextActor)
  extends ServiceHandler[SaveUserCommand, SaveUserCommandResponse] {

  import system.dispatcher

  override def handleMessage(request: SaveUserCommand): Future[SaveUserCommandResponse] = {
    val idUser = UUID.randomUUID().toString
    usersBoundedContextActor
      .sendMessageToOurDatabase(UsersRepositoryMessages.SaveUser(UserModel(request.name, idUser)))
      .mapTo[UsersRepositoryMessages.SaveUserResponse]
      .map{ _ => SaveUserCommandResponse(idUser) }
  }

}
