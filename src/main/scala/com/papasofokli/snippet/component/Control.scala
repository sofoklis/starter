package com.papasofokli.snippet.component

import net.liftweb.http._
import S._
import js._
import JsCmds._
import JE._
import net.liftweb.util._
import Helpers._
import scala.xml.Text
import com.papasofokli.util.component.ValidationResult._
import com.papasofokli.util.component._

trait ControlT {
  def fieldName() = S.attr("name") openOr "fieldName"

  def validator = Control.Validator

  def value() = validator.value(fieldName)

  def id: String = validator.field(fieldName).id

  def label: String = validator.field(fieldName).label

}

trait SingelValueControlT extends ControlT {
  /**
   * First updates the map with the value and the validation, and then sends the ajax response
   */
  def callback(fn: String)(value: String): net.liftweb.http.js.JsCmd =
    {
      val fnlc = validator.field(fn)
      validator.setValue(fn, value)
      val res = validator.validationResult(fn)
      // Combine the validator ajax response with the field ajax response and send both back,
      // this way you can enable disable buttons etc
      fnlc.ajaxResponse() & validator.ajaxResponse()
    }
}

class Control extends ControlT {

  def render: CssSel =
    {
      "#fieldNameControl [id]" #> (fieldName + "Control" + id) &
        "#fieldNameHelp [id]" #> (fieldName + "Help" + id) &
        "#fieldNameLabel *" #> label &
        "#fieldNameLabel [id]" #> (fieldName + "Label" + id)
    }

}

object Control {
  object Validator extends RequestVar[FieldValidator](EmptyFieldValidator)
}