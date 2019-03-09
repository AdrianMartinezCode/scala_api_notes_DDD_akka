import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

object WebServer{
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    var notes: List[Note] = Nil

    final case class Note(name:String, text:String, id:Long)
    final 

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
