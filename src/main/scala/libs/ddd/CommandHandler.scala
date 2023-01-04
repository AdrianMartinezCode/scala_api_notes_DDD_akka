package libs.ddd

import akka.util.Timeout
import libs.akka.GlobalActorSystem

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.reflect.runtime.universe._

abstract class CommandHandler[Request <: Command: TypeTag, Response] {

  implicit val system = GlobalActorSystem.system
  implicit val ec = system.dispatcher

  def handleMessage(request: Request): Future[Response]
  def getName: String = typeOf[Request].toString
}
