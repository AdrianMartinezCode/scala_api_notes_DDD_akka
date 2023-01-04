package modules.users.domain

import modules.notes.domain.Note
import modules.users.database.models.UserModel


final case class User(name:String, idUser:String, notes:List[Note])
