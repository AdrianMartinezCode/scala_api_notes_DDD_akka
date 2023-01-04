package libs.database

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.language.postfixOps


class DatabaseClient[T <: DatabaseModel](actorSystem: ActorSystem, poolSize: Int, entityName: String)
  extends DatabasePort[T] {

  import ActorOracleMessages._
  import DatabaseMessages._

  implicit val timeout: Timeout = Timeout(3 seconds)

  private val actors: Map[String, ActorRef] =
    (1 to poolSize).map { i =>
      val future = Try(Future(getActorName(i) -> actorSystem.actorOf(Props[Database[T]], getActorName(i))))
        .getOrElse(actorSystem.actorSelection(getActorName(i)).resolveOne.map(getActorName(i) -> _))
      Await.result(future, 3 seconds)
    }.toMap

  private val oracle = actorSystem.actorOf(Props[ActorOracle], s"Oracle--$entityName")
  private def getActorName(i: Int) = s"$entityName--$i"

  private def getRefActorByIdEntity(idEntity: String): Future[Option[String]] =
    (oracle ? GetRefActorByIdEntity(idEntity))
      .mapTo[GetRefActorByIdEntityResponse]
      .map(_.refActorOpt)

  def getEntity(idEntity: String) : Future[Option[T]] = {
    getRefActorByIdEntity(idEntity)
      .flatMap(
        _.fold(Future.successful[Option[T]](None)){ refActorString =>
          // we will to assume, that the actor references and oracle stored references always are coherent
          val actorRef = actors.getOrElse(refActorString, throw new Exception("Actor not found"))
          (actorRef ? GetEntityByIdEntity)
            .mapTo[GetEntityByIdEntityResponse[T]]
            .map(_.entity)
        }
      )
  }

  def getAllEntities: Future[List[T]] = {
    actors.map(_._2 ? GetAllEntities())
      .map(_.mapTo[GetAllEntitiesResponse[T]].map(_.entities))
      .foldLeft(Future.successful(List[T]()))((acc, v) => acc.flatMap(l => v.map(_ ++ l)))
  }

  def saveEntity(entity: T) : Future[Option[T]] = {
    getRefActorByIdEntity(entity.idEntity)
      .flatMap(_.fold(
        (oracle ? SetIdEntityRefActor(entity.idEntity))
          .mapTo[SetIdEntityRefActorResponse]
          .map(_.refActor)
      )(Future.successful))
      .flatMap{ refActor =>
        val actor = actors.getOrElse(refActor, throw new Exception("actor not found"))
        (actor ? SaveEntity(entity))
          .mapTo[SaveEntityResponse[T]]
          .map(_.oldPossibleEntity)
      }
  }

  def deleteEntity(idEntity: String) : Future[Either[EntityNotFoundException, Option[T]]] = {
    getRefActorByIdEntity(idEntity)
      .flatMap{
        case Some(refActor) =>
          val actor = actors.getOrElse(refActor, throw new Exception("actor not found"))
          (actor ? RemoveEntity(idEntity))
            .mapTo[RemoveEntityResponse[T]]
            .map(_.entity)
            .map(e => Right(e))
        case None => Future.successful(Left(new EntityNotFoundException()))
      }
  }
}
