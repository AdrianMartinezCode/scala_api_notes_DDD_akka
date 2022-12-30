package modules.users.commands.modifyuser

case class ModifyUserCommand(idUser: String, name: Option[String])

type ModifyUserCommandResponse = Either[String, String]