package libs.ddd

import scala.concurrent.Future

class CommandBus[T <: Command, R] {

  private val handlers = Map[String, CommandHandler[T, R]]()

  def registerHandler(handler: CommandHandler[T, R]): Map[String, CommandHandler[T, R]] =
    handlers + (handler.getName -> handler)

  def execute(command: T)(implicit commandName: String): Future[R] = {
    val handler = handlers.getOrElse(commandName, throw new IllegalStateException(s"Handler $commandName not defined"))
    handler.handleMessage(command)
  }
}
