package server.service

import akka.stream.javadsl.RunnableGraph
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.{Future, Promise}

object pruba2 extends App {

  case class Request[T](message: T)
  case class Response[R](response: R)

  val serviceHandler : Request[String] => Future[Response[String]] = r => Future.successful(Response(r.message + "##HELLO"))


  val requestFlow: Flow[Request[_], Future[Response[_]], _] = Flow.fromFunction { request =>
    val responsePromise = Promise[Response[_]]
    val requestSource = Source.single(request)
    val responseSink = Sink.foreach{ response =>
      responsePromise.completeWith(serviceHandler(response))
    }
    val runnableGraph: RunnableGraph[Future[Response[_]]] =
      requestSource.via(bidiFlow).to(responseSink)

  }


  def emit[T, R](message: Request[T]) : Future[R] = {

  }





}
