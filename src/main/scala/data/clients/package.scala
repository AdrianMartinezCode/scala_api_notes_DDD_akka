package data

import akka.actor.Actor


class UserClient extends Actor {



  def receive: Receive = {
    case GetUsers("")
  }
}
