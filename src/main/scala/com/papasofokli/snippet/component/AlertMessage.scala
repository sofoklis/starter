package com.papasofokli.snippet.component
import net.liftweb.http.S
import net.liftweb.common.Box
import scala.xml.NodeSeq
import net.liftweb.util.BindHelpers._
import net.liftweb.common.Loggable

object AlertType extends Enumeration {
  type AlertType = Value
  val Success = Value(0, "success")
  val Error = Value(1, "error")
  val Warning = Value(2, "block")
  val Info = Value(3, "info")
}

import AlertType._
class AllertMessage(message: String, messageType: AlertType) extends Loggable {
  def render = {
    logger.info(s"message: $message type: $messageType:")

    "#alert [class+]" #> ("alert-" + messageType) &
      "#message *" #> message
  }

}

object AlertMessage {

  def render(message: String, allertType: AlertType): Box[NodeSeq] = {
    S.runTemplate(
      "templates-hidden" :: "component" :: "alertMessage" :: Nil,
      "component/AlertMessage" -> (new AllertMessage(message, allertType).render(_)))
  }
}