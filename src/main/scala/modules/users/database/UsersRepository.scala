package modules.users.database

import akka.Done
import akka.actor.{Actor, ActorLogging, ActorSystem}
import libs.akka.GlobalActorSystem
import libs.database.{DatabaseClient, DatabasePort}
import modules.users.database.models.UserModel

import scala.concurrent.{ExecutionContextExecutor, Future}
import modules.users.database.preseed.DefinedUsers
import modules.users.database.messages.UsersRepositoryMessages

import scala.collection.mutable
import scala.collection.mutable.Map

class UsersRepository(actorSystem: ActorSystem, poolSize: Int)
  extends DatabaseClient[UserModel](actorSystem, poolSize, "User") {



//  implicit val system: ActorSystem = GlobalActorSystem.system
//  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

//  import UsersRepositoryMessages._

//  private val users_db: mutable.Map[String, UserModel] = mutable.Map()

//  override def preStart(): Unit = initBd()

//  private def initBd(): Future[Done] = {
//    DefinedUsers.predefinedUsersDb.foreach(user => users_db += (user.idUser -> user))
//    Future {
//      Done
//    }
//  }

//  override def receive: Receive = {
//    case GetUser(idUser) => sender() ! GetUserResponse(fetchUser(idUser))
//    case GetUsers => sender() ! GetUsersResponse(getAllUsers)
//    case SaveUser(model) => {
//      saveUser(model)
//      sender() ! SaveUserResponse()
//    }
//  }
//
//  private def fetchUser(idUser: String): Option[UserModel] = {
//    users_db.get(idUser)
//  }
//
//  private def saveUser(users_par: UserModel): Unit = {
//    users_db += (users_par.idUser -> users_par)
//    ()
//  }
//
//  private def getAllUsers: List[UserModel] = {
//    users_db.values.toList
//  }


}
