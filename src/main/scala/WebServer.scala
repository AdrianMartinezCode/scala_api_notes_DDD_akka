import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.StdIn

object WebServer{
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    var users_db: List[User] = Nil

    final case class Note(name:String, text:String, id:Long)
    final case class User(name:String, id:Long, notes:List[Note])
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

    val route1 =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say Hello </h1>"))
        }
      } ~ path("ja") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say Ja </h1>"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route1, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress return to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
