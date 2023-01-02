package libs.database

import scala.concurrent.Future

trait DatabasePort[T <: DatabaseModel] {
  def getEntity(idEntity: String) : Future[Option[T]]
  def getAllEntities: Future[List[T]]
  def saveEntity(entity: T) : Future[Option[T]]
  def deleteEntity(idEntity: String) : Future[Either[EntityNotFoundException, Option[T]]]
}
