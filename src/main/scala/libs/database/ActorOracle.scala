package libs.database

import akka.actor.Actor

import scala.collection.mutable

object ActorOracleMessages {
  case class GetRefActorByIdEntity(idEntity: String)
  case class GetRefActorByIdEntityResponse(refActorOpt: Option[String])
  case class SetIdEntityRefActor(idEntity: String, refActor: String)
  case class RemoveIdEntity(idEntity: String)
}

class ActorOracle extends Actor {
  import ActorOracleMessages._

  private val users: mutable.Map[String, String] = mutable.Map.empty
  private val refActors: mutable.Map[String, mutable.Set[String]] = mutable.Map.empty

  override def receive: Receive = {
    case GetRefActorByIdEntity(idEntity) => sender() ! GetRefActorByIdEntityResponse(users.get(idEntity))
    case SetIdEntityRefActor(idEntity, refActor) =>
      users += (idEntity -> refActor)
      refActors.get(refActor)
        .fold(refActors += (refActor -> Set(idEntity)))(
          s => s += idEntity
        )
    case RemoveIdEntity(idEntity) =>
      users.get(idEntity).fold()(refActor => {
        users - idEntity
        refActors.get(refActor).fold()(s => s - idEntity)
      })

  }
}
