package server.service

import akka.actor.ActorSystem
import akka.stream.actor.ActorSubscriberMessage
import akka.stream.scaladsl.{BidiFlow, Flow, Keep, Sink, Source}
import akka.stream.{ActorMaterializer, OverflowStrategy, QueueOfferResult}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise}




class MyPublisher[T, R] {


}

object pruba extends App {

  implicit val system = ActorSystem("BidirectionalFlows")
  implicit val materializer = ActorMaterializer()

  implicit val system = ActorSystem("BidirectionalFlows")
  implicit val materializer = ActorMaterializer()

  import akka.stream.scaladsl.{Source, Sink, RunnableGraph}
  import scala.concurrent.{Future, Promise}

  // Define the request and response types
  case class Request[T](id: Int, message: T)

  case class Response[R](id: Int, message: R)

  // Define the request flow
  val requestFlow: Flow[Request[String], Future[Response[String]], _] = Flow.fromFunction { request =>
    // Emit the request and return a Future[Response]
    def emit[T, R](req: Request[T]): Future[Response[R]] = {
      // Create a Promise for the response
      val responsePromise = Promise[Response[R]]
      // Create a Source and a Sink for the request and response
      val requestSource = Source.single(request)
      val responseSink = Sink.foreach[Response[R]] { response =>
        // Complete the Promise with the response
        responsePromise.success(response)
      }
      // Connect the Source and Sink to the BidiFlow
      val runnableGraph: RunnableGraph[Future[Response[R]]] =
        requestSource.via(bidiFlow).to(responseSink)
      // Run the graph and return the response Future
      runnableGraph.run()
      responsePromise.future
    }

    emit(request).map { response =>
      // Modify the response message
      response.copy(message = response.message.toLowerCase)
    }
  }

  // Define the response flow
  val responseFlow: Flow[Response[String], Response[String], _] = Flow[Response[String]].map { response =>
    // Modify the response message
    response.copy(message = response.message.toLowerCase)
  }

  // Combine the request and response flows into a BidiFlow
  val bidiFlow: BidiFlow[Request[String], Future[Response[String]], Response[String], Response[String], _] = BidiFlow.fromFlows(requestFlow, responseFlow)

  val response: Future[Response[String]] = bidiFlow.emit(Request(1, "Hello"))
  response.foreach(println) // prints the response

}
