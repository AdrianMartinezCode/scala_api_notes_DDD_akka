package libs.akka

import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.duration.DurationInt

trait DefaultController {
  implicit val system = GlobalActorSystem.system
  implicit val materializer = ActorMaterializer()
  implicit val defaultTimeout = Timeout(2 seconds)
}
