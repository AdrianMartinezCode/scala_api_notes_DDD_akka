package modules.users.commands.modifyuser

import libs.ddd.{Command}
import modules.users.domain.User

case class ModifyUserCommand(idUser: String, name: Option[String]) extends Command


object ModifyUserCommandResponse {
  def apply(exception: Exception): ModifyUserCommandResponse = ModifyUserCommandResponse(Left(exception))

  def apply(user: Option[User]): ModifyUserCommandResponse = ModifyUserCommandResponse(Right(user))
}
case class ModifyUserCommandResponse(result: Either[Exception, Option[User]]) {

}
