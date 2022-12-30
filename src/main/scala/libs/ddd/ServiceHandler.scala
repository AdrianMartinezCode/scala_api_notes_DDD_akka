package libs.ddd

import akka.util.Timeout
import libs.akka.GlobalActorSystem

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

trait ServiceHandler[Request, Response] {
  def handleMessage(request: Request): Future[Response]
  implicit val system = GlobalActorSystem.system
  implicit val defaultTimeout = Timeout(2 seconds)
}
