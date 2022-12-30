package modules.users.commands.modifyuser

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import libs.ddd.ServiceHandler
import modules.users.akka.UsersBoundedContextActor
import modules.users.database.messages.UsersRepositoryMessages.{GetUser, GetUserResponse, SaveUser, SaveUserResponse}
import modules.users.database.models.UserModel

import scala.concurrent.Future

class ModifyUserService(usersBoundedContextActor: UsersBoundedContextActor)
  extends ServiceHandler[ModifyUserCommand, ModifyUserCommandResponse] {

  import system.dispatcher

  override def handleMessage(request: ModifyUserCommand): Future[ModifyUserCommandResponse] =
    usersBoundedContextActor.sendMessageToOurDatabase[GetUser, GetUserResponse](GetUser(request.idUser))
      .flatMap( opt => opt.user match {
        case None => Future.apply(Left("User not found"))
        case Some(user) => request.name
          .map { name => UserModel(name, request.idUser) }
          .map(userModel => usersBoundedContextActor
            .sendMessageToOurDatabase(SaveUser(userModel))
            .mapTo[SaveUserResponse]
            .map { _ => Right("Completed") }
          )
        .getOrElse(Future.apply(Left("Name not specified")))
      })

}
