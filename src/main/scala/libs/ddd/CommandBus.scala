package libs.ddd

import scala.collection.mutable
import scala.concurrent.Future

class CommandBus {

  private val handlers = mutable.Map[String, CommandHandler[_, _]]()

  def registerHandler[Request <: Command, Response](handler: CommandHandler[Request, Response]) = {
    val name = handler.getName
    println(name)
    handlers += (name -> handler)
    Unit
  }

  def execute[T <: Command, R](command: T)(implicit commandName: String): Future[R] = {
    val handler = handlers.getOrElse(commandName, throw new IllegalStateException(s"Handler $commandName not defined"))
    handler.asInstanceOf[CommandHandler[T, R]].handleMessage(command)
  }
}
