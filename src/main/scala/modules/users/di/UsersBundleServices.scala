package modules.users.di

import libs.akka.GlobalActorSystem
import libs.ddd.CommandHandler
import modules.users.commands.modifyuser.ModifyUserService
import modules.users.commands.saveuser.SaveUserService
import modules.users.database.UsersRepository
import modules.users.query.getuser.GetUserService
import modules.users.query.getusers.GetUsersService

object UsersBundleServices {

  val actorSystem = GlobalActorSystem.system

  val repository = new UsersRepository(actorSystem, 10)

  val services = List(
    new ModifyUserService(repository),
    new SaveUserService(repository),
    new GetUsersService(repository),
    new GetUserService(repository)
  )

  def getServices: List[CommandHandler[_, _]] = services
}
