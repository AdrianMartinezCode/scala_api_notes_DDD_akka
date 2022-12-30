package modules.users.akka

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import libs.akka.DefaultActor
import modules.notes.akka.{NotesBoundedContext, NotesBoundedContextActor}
import modules.users.commands.modifyuser.{ModifyUserCommand, ModifyUserHttpController, ModifyUserService}
import modules.users.commands.saveuser.{SaveUserCommand, SaveUserService}
import modules.users.database.UsersRepository
import modules.users.query.getuser.GetUser

import scala.concurrent.Future
import modules.users.query.getuser.GetUserService
import modules.users.query.getusers.{GetUsers, GetUsersService}

class UsersBoundedContextActor extends DefaultActor with UsersBoundedContext {

  private val repository: ActorRef = system.actorOf(Props[UsersRepository])
  private val notesBoundedContext = system.actorOf(Props[NotesBoundedContextActor])

  // TODO extract these initializations outer this class
  private val getUserService = new GetUserService(this)
  private val getUsersService = new GetUsersService(this)
  private val saveUserService = new SaveUserService(this)
  private val modifyUserService = new ModifyUserService(this)

  def sendMessageToOurDatabase[Q, A](question: Q): Future[A] =
    (repository ? question).mapTo[A]

  def sendMessageToNotesBoundedContext[Q, A](question: Q): Future[A] =
    (notesBoundedContext ? question).mapTo[A]

  override def receive: Receive = {
    case m: GetUser => getUserService.handleMessage(m)
    case m: GetUsers => getUsersService.handleMessage(m)
    case m: SaveUserCommand => saveUserService.handleMessage(m)
    case m: ModifyUserCommand => modifyUserService.handleMessage(m)
  }
}
