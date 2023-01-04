package di

import libs.ddd.CommandBus
import modules.users.di.UsersBundleServices

object AppBundleCommandBus {

  val bus = new CommandBus

  def getBus: CommandBus = bus

}
