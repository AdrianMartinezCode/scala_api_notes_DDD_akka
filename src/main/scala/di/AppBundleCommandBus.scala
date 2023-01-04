package di

import libs.ddd.CommandBus
import modules.users.di.UsersBundleServices

object AppBundleCommandBus {

  val bus = new CommandBus[_, _]

  UsersBundleServices.getServices.foreach(bus.registerHandler)

  def getBus: CommandBus[_, _] = bus

}
