package modules.users.database

import libs.database.DatabasePort
import modules.users.database.models.UserModel

trait UsersRepositoryPort extends DatabasePort[UserModel] {

}
