package libs.akka

import akka.stream.ActorMaterializer
import akka.util.Timeout
import libs.ddd.{Command, CommandBus, CommandHandler}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.reflect.runtime.universe._

abstract class DefaultController[C <: Command: TypeTag, R](commandBus: CommandBus) {
  implicit val system = GlobalActorSystem.system
  implicit val defaultTimeout = Timeout(2 seconds)
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  implicit def getCommandName : String = typeOf[C].toString

  def sendCommand(command: C): Future[R] = commandBus.execute(command)

}
