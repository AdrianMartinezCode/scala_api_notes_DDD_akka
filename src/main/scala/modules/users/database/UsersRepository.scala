package modules.users.database

import akka.Done
import akka.actor.{Actor, ActorLogging, ActorSystem}
import libs.akka.GlobalActorSystem
import modules.users.database.models.UserModel

import scala.concurrent.{ExecutionContextExecutor, Future}
import modules.users.database.preseed.DefinedUsers
import modules.users.database.messages.UsersRepositoryMessages

import scala.collection.mutable.Map

class UsersRepository extends Actor with ActorLogging {

  implicit val system = GlobalActorSystem.system
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  import UsersRepositoryMessages._

  var users_db: Map[String, UserModel] = Map()

  override def preStart() = initBd()

  private def initBd(): Future[Done] = {
    DefinedUsers.predefinedUsersDb.foreach(user => users_db += (user.idUser -> user))
    Future {
      Done
    }
  }

  override def receive: Receive = {
    case GetUser(idUser) => sender() ! GetUserResponse(fetchUser(idUser))
    case GetUsers => sender() ! GetUsersResponse(getAllUsers)
    case SaveUser(model) => {
      saveUser(model)
      sender() ! SaveUserResponse()
    }
  }

  private def fetchUser(idUser: String): Option[UserModel] = {
    users_db.get(idUser)
  }

  private def saveUser(users_par: UserModel) = {
    users_db += (users_par.idUser -> users_par)
    ()
  }

  private def getAllUsers: List[UserModel] = {
    users_db.values.toList
  }

}
