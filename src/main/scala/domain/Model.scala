package domain

final case class User(name:String, idUser:Long, notes:List[Note])
final case class Note(name:String, text:String, idUser:Long)
