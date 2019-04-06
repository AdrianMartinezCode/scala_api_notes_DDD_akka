package data



package object model {
  final case class User(name:String, id:Long, notes:List[Note])
  final case class Note(name:String, text:String, id:Long)
}
