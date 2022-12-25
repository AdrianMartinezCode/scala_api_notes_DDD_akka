package modules.users.domain

import modules.notes.domain.Note


final case class User(name:String, idUser:String, notes:List[Note])
