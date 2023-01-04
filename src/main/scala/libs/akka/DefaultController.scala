package libs.akka

import akka.stream.ActorMaterializer
import akka.util.Timeout
import libs.ddd.{Command, CommandBus, CommandHandler}

import scala.concurrent.duration.DurationInt

abstract class DefaultController[C <: Command, R](commandBusUntyped: CommandBus[_, _]) {
  implicit val system = GlobalActorSystem.system
  implicit val defaultTimeout = Timeout(2 seconds)
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  implicit def getCommandName : String = classOf[C].getName

  val commandBus : CommandBus[C, R] = commandBusUntyped.asInstanceOf[CommandBus[C, R]]

}
