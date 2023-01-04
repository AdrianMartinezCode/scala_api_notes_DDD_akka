package modules.users.query.getuser

import libs.ddd.Command
import modules.users.domain.User

case class GetUserQuery(idUser: String) extends Command
case class GetUserQueryResponse(userOption: Option[User])