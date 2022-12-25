package libs.api

import akka.http.scaladsl.server.Route

trait StandardHttpController {
  def getRoute : Route
}
