package modules.users.commands.saveuser

import libs.ddd.Command

case class SaveUserCommand(name: String) extends Command
case class SaveUserCommandResponse(idUser: String)
