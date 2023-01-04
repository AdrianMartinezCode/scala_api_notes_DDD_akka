package libs.ddd

import akka.util.Timeout
import libs.akka.GlobalActorSystem

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

trait CommandHandler[Request <: Command, Response] {
  def handleMessage(request: Request): Future[Response]
  implicit val system = GlobalActorSystem.system
  implicit val ec = system.dispatcher

  def getName: String = classOf[Request].getName
}
