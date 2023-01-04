package modules.users.di

import di.AppBundleCommandBus
import libs.api.StandardHttpController
import modules.users.commands.modifyuser.ModifyUserHttpController
import modules.users.commands.saveuser.SaveUserHttpController
import modules.users.query.getuser.GetUserHttpController
import modules.users.query.getusers.GetUsersHttpController

object UsersBundleControllers {

  val bus = AppBundleCommandBus.getBus

  val controllers = List(
    new ModifyUserHttpController(bus),
    new SaveUserHttpController(bus),
    new GetUsersHttpController(bus),
    new GetUserHttpController(bus)
  )

  def getControllers: List[StandardHttpController] = controllers
}
