package modules.notes.config

import modules.notes.domain.Note
import spray.json.{DefaultJsonProtocol, JsonFormat}

trait NoteJsonProtocol extends DefaultJsonProtocol {
  implicit val noteFormat: JsonFormat[Note] = jsonFormat3(Note)
}
