package modules.users.database.messages

import modules.users.database.models.UserModel

object UsersRepositoryMessages {
  case class GetUser(idUser: String)
  case class GetUserResponse(user: Option[UserModel])
  case class GetUsers()
  case class GetUsersResponse(users: List[UserModel])
  case class SaveUser(user: UserModel)
  case class SaveUserResponse()
}
