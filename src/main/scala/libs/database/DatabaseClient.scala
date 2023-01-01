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


class DatabaseClient[T](actorSystem: ActorSystem, poolSize: Int, entityName: String) {

  import ActorOracleMessages._
  import DatabaseMessages._

  implicit val timeout: Timeout = Timeout(3 seconds)

  private val actors: Map[String, ActorRef] =
    (1 to poolSize).map { i =>
      val future = Try(Future(getActorName(i) -> actorSystem.actorOf(Props[Database[T]], getActorName(i))))
        .getOrElse(actorSystem.actorSelection(getActorName(i)).resolveOne.map(getActorName(i) -> _))
      Await.result(future, 3 seconds)
    }.toMap

  private val oracle = actorSystem.actorOf(Props[ActorOracle], s"Oracle##$entityName")
  private def getActorName(i: Int) = s"$entityName##$i"

  private def getRefActorByIdEntity(idEntity: String): Future[Option[String]] =
    (oracle ? GetRefActorByIdEntity(idEntity))
      .mapTo[GetRefActorByIdEntityResponse]
      .map(_.refActorOpt)

  def getEntity(idEntity: String) : Future[Option[T]] =
    getRefActorByIdEntity(idEntity)
      .map(
        _.map { refActorString =>
          // we will to assume, that the actor references and oracle stored references always are coherent
          val actorRef = actors.getOrElse(refActorString, throw new Exception("Actor not found"))
          (actorRef ? GetEntityByIdEntity)
            .mapTo[GetEntityByIdEntityResponse[T]]
            .map(_.entity)
        }
      )
}
