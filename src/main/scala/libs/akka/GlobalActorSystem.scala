package libs.akka

import akka.actor.ActorSystem

object GlobalActorSystem {

  val system = ActorSystem("LowLevelRest")
}
