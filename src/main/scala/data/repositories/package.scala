package data

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import data.model.User

import scala.concurrent.Future


package object repositories {


  object UsersRepository {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    var users_db: List[User] = Nil

    final case class DB(users: List[User])

    def fetchUser(idUser:Long): Future[Option[User]] = Future {
      users_db.find(u => u.id == idUser)
    }
    def saveUser(db: DB): Future[Done] = {
      users_db = db match {
        case DB(users) => users ::: users_db
        case _         => users_db
      }
      Future {Done}
    }

  }

  object NotesRepository {

  }
}
