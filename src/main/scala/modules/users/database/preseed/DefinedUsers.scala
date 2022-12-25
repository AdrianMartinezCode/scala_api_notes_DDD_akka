package modules.users.database.preseed

import modules.users.database.models.UserModel

import java.util.UUID

object DefinedUsers {
  val user1 = UserModel("Feride Astrape", "1")
  val user2 = UserModel("Odilo Domitille", "2")

  val predefinedUsersDb = List(user1, user2)

  println(predefinedUsersDb)
}
