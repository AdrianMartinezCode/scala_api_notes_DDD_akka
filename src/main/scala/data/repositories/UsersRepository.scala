package data.repositories

import akka.Done
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import domain.{Note, User}

import scala.concurrent.{ExecutionContextExecutor, Future}


object UsersRepository {
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  var users_db: List[User] = Nil

  //final case class DB(users: List[User])

  def initBd() : Future[Done] = {
    val note1 = Note("nota1", "text random de note", 1)
    val note2 = Note("nota2", "text random notee 2", 2)
    val note3 = Note("SUMINISTRES", "SUMINISTRES PER NOTES", 3)
    val note4 = Note("NOSUMS", "ASDSADASD", 4)

    val user1 = User("adrian", 1, List(note1, note2))
    val user2 = User("antonio", 2, List(note3, note4))
    users_db =  List(user1,user2)
    Future {Done}
  }

  def fetchUser(idUser:Long): Future[Option[User]] = Future {
    users_db.find(u => u.id == idUser)
  }
  def saveUser(users_par: List[User]): Future[Done] = {
    users_db = users_par match {
      case users => users ::: users_db
      case _         => users_db
    }
    Future {Done}
  }
  def getAllNotes: Future[List[Note]] = Future {
    users_db
      .flatMap(l => Seq(l))
      .flatMap(u => u.notes)
  }
  def getAllUsers: Future[List[User]] = Future {
    users_db
  }

}

