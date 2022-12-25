package modules.users.query.getusers

import modules.users.domain.User

case class GetUsers()
case class GetUsersResponse(users: List[User])
