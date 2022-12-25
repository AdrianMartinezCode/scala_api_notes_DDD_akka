package libs.akka

import akka.actor.Actor
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.duration.DurationInt

abstract class DefaultActor extends Actor {
  implicit val system = GlobalActorSystem.system
  implicit val materializer = ActorMaterializer()
  implicit val defaultTimeout = Timeout(2 seconds)
}
