package libs.database

import akka.actor.Actor

import scala.collection.mutable

object ActorOracleMessages {
  case class GetRefActorByIdEntity(idEntity: String)
  case class GetRefActorByIdEntityResponse(refActorOpt: Option[String])
  case class SetIdEntityRefActor(idEntity: String)
  case class SetIdEntityRefActorResponse(refActor: String)
  case class RemoveIdEntity(idEntity: String)
}

class ActorOracle extends Actor {
  import ActorOracleMessages._

  private val users: mutable.Map[String, String] = mutable.Map.empty
  private val refActors: mutable.Map[String, mutable.Set[String]] = mutable.Map.empty

  override def receive: Receive = {
    case GetRefActorByIdEntity(idEntity) => sender() ! GetRefActorByIdEntityResponse(users.get(idEntity))
    case SetIdEntityRefActor(idEntity) =>
      val refActor = getNextRefActor
      users += (idEntity -> refActor)
      refActors.get(refActor)
        .fold(refActors += (refActor -> Set(idEntity)))(
          s => s += idEntity
        )
      sender() ! SetIdEntityRefActorResponse(refActor)
    case RemoveIdEntity(idEntity) =>
      users.get(idEntity).fold()(refActor => {
        users - idEntity
        refActors.get(refActor).fold()(s => s - idEntity)
      })

  }

  private def getNextRefActor: String = {
    refActors
      .toList
      .map{ case (k, v) => (k, v.size) }
      .min(Ordering.by[(String, Int), Int](_._2))
      ._1
  }
}
