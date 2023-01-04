package modules.users.di

import akka.actor.ActorSystem
import di.AppBundleCommandBus
import libs.akka.GlobalActorSystem
import libs.ddd.{CommandBus, CommandHandler}
import modules.users.commands.modifyuser.ModifyUserService
import modules.users.commands.saveuser.SaveUserService
import modules.users.database.UsersRepository
import modules.users.query.getuser.GetUserService
import modules.users.query.getusers.GetUsersService

object UsersBundleServices {

  val actorSystem: ActorSystem = GlobalActorSystem.system

  val repository = new UsersRepository(actorSystem, 10)

  val modifyUser = new ModifyUserService(repository)
  val saveUser = new SaveUserService(repository)
  val getUsers = new GetUsersService(repository)
  val getUser = new GetUserService(repository)

  val bus: CommandBus = AppBundleCommandBus.getBus

  bus.registerHandler(modifyUser)
  bus.registerHandler(saveUser)
  bus.registerHandler(getUsers)
  bus.registerHandler(getUser)

}
