package libs.utils

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.stream.scaladsl.{Sink, Source}

class Subject {

  // Create an actor system and materializer
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  // Create a subject by creating a source and sink and attaching them together
  val subject = Source.queue[Int](1, OverflowStrategy.dropNew)
    .to(Sink.fromSubscriber(ReactiveSubscriber[Int]))
    .run()

  subject.

  // You can now use the subject as a publisher or subscriber
  subject.publisher.subscribe(ReactivePublisher[Int])
  subject.subscriber.onNext(1)


}
