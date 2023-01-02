package modules.users.database.models

import libs.database.DatabaseModel

case class UserModel(name: String, override val idEntity: String) extends DatabaseModel(idEntity)
