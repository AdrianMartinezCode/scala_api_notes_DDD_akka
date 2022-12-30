package modules.notes.database.messages

import modules.notes.database.models.NoteModel

object NotesRepositoryMessages {
  case class GetNotesByIdUser(idUser: String)
  case class GetNotesByIdUserResponse(notes: List[NoteModel])

  case class GetNote(idNote: String)
  case class GetNoteResponse(note: Option[NoteModel])

  case class GetNotes()
  case class GetNotesResponse(notes: List[NoteModel])

  case class SaveNote(note: NoteModel)
  case class SaveNoteResponse()
}
