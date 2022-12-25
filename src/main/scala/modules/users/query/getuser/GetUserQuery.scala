package modules.users.query.getuser

import modules.users.domain.User

case class GetUser(idUser: String)
case class GetUserResponse(userOption: Option[User])