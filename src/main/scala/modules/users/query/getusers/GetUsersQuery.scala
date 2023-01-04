package modules.users.query.getusers

import libs.ddd.Command
import modules.users.domain.User

case class GetUsersQuery() extends Command
case class GetUsersQueryResponse(users: List[User])
