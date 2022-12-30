package modules.users.di

import akka.actor.{ActorSystem, Props}
import libs.akka.GlobalActorSystem
import libs.api.StandardHttpController
import modules.users.akka.UsersBoundedContextActor
import modules.users.commands.modifyuser.ModifyUserHttpController
import modules.users.commands.saveuser.SaveUserHttpController
import modules.users.query.getuser.GetUserHttpController
import modules.users.query.getusers.GetUsersHttpController

object UsersContextBundle {

  implicit val system: ActorSystem = GlobalActorSystem.system

  private val actorRef = system.actorOf(Props[UsersBoundedContextActor])

  private val controllers = List(
    new GetUsersHttpController(actorRef),
    new GetUserHttpController(actorRef),
    new SaveUserHttpController(actorRef),
    new ModifyUserHttpController(actorRef)
  )

  def getControllers: List[StandardHttpController] = controllers
}
