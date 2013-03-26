package com.papasofokli.util.component
import net.liftweb.http._
import net.liftweb.http.S._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import net.liftweb.util.Helpers
import net.liftweb.util.AnyVar.whatVarIs

trait FieldValidator {
  def ajaxResponse(): JsCmd = Noop

  def fieldNameIdCallbackMap: Map[String, InputField]
  def selectNameIdCallbackMap: Map[String, SelectField]

  private def allAjaxResponses = fieldNameIdCallbackMap.map(_._2.ajaxResponse()) ++ selectNameIdCallbackMap.map(_._2.ajaxResponse())

  def ajaxUpdateAll(): JsCmd = (Noop /: allAjaxResponses)(_ & _) & ajaxResponse()

  def field(fieldName: String): FieldNameIdLabelCallBack = {
    if (selectNameIdCallbackMap.contains(fieldName))
      selectNameIdCallbackMap(fieldName)
    else inputField(fieldName)
  }

  def inputField(fieldName: String) = fieldNameIdCallbackMap.getOrElse(fieldName, new InputField(fieldName, getNextId, fieldName))

  def getNextId = Helpers.nextFuncName

  def setValue(fieldName: String, value: String) = FieldValueValidationMap.vvMap.setValue(this)(fieldName)(value)
  def value(fieldName: String) = FieldValueValidationMap.vvMap.value(fieldName)
  def validationResult(fieldName: String) = FieldValueValidationMap.vvMap.validation(fieldName)

}

object EmptyFieldValidator extends FieldValidator {
  var fieldNameIdCallbackMap: Map[String, InputField] = Map()
  var selectNameIdCallbackMap: Map[String, SelectField] = Map()
}

trait FieldNameIdLabelCallBack {
  def name: String
  def id: String
  def label: String
  def validateFunction: () ⇒ ValidationHelpText
  def ajaxResponse: () ⇒ JsCmd
}

case class InputField(name: String, id: String, label: String,
  validateFunction: () ⇒ ValidationHelpText = () ⇒ ValidationHelpText.allGood,
  ajaxResponse: () ⇒ JsCmd = () ⇒ BootstrapFieldValidator.noopCallback)
  extends FieldNameIdLabelCallBack

case class SelectField(name: String, id: String, label: String,
  validateFunction: () ⇒ ValidationHelpText = () ⇒ ValidationHelpText.allGood,
  ajaxResponse: () ⇒ JsCmd = () ⇒ BootstrapFieldValidator.noopCallback,
  optionList: List[String]) extends FieldNameIdLabelCallBack

object FieldNameIdLabelCallback {
  def apply(name: String, id: String, label: String) = new InputField(name, id, label)
  def default(name: String, id: String, label: String) = InputField(name, id, label)
}

