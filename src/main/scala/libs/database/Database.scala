package libs.database

import akka.actor.Actor

import scala.collection.mutable

object DatabaseMessages {
  case class GetEntityByIdEntity(idEntity: String)
  case class GetEntityByIdEntityResponse[T](entity: Option[T])
  case class GetAllEntities()
  case class GetAllEntitiesResponse[T](entities: List[T])
  case class SaveEntity[T](entity: T)
  case class SaveEntityResponse[T](oldPossibleEntity: Option[T])
  case class RemoveEntity(idEntity: String)
  case class RemoveEntityResponse[T](entity: Option[T])
}

class Database[T <: DatabaseModel] extends Actor {

  import DatabaseMessages._

  val entities: mutable.Map[String, T] = mutable.Map.empty

  override def receive: Receive = {
    case GetEntityByIdEntity(idEntity) => sender() ! GetEntityByIdEntityResponse(entities.get(idEntity))
    case GetAllEntities => sender() ! GetAllEntitiesResponse(entities.values.toList)
    case SaveEntity(entity: T) => {
      val oldEntity = entities.get(entity.idEntity)
      entities + (entity.idEntity -> entity)
      sender() ! SaveEntityResponse(oldEntity)
    }
    case RemoveEntity(idEntity) => {
      val oldEntity = entities.get(idEntity)
      entities - idEntity
      sender() ! RemoveEntityResponse(oldEntity)
    }
  }
}
