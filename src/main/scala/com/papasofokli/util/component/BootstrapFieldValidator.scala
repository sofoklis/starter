package com.papasofokli.util.component
import net.liftweb.http._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import com.papasofokli.util.component.ValidationResult._
import net.liftweb.http.js.JsExp.boolToJsExp
import net.liftweb.http.js.JsExp.strToJsExp

object BootstrapFieldValidator {

  val noopCallback: net.liftweb.http.js.JsCmd = JsCmds.Noop

  def updateControlGroupClassAndHelpMessage(validator: FieldValidator)(fieldName: String): () ⇒ (net.liftweb.http.js.JsCmd) = () ⇒
    {
      val res = validator.validationResult(fieldName)
      updateControlGroupClassAndHelpMessage(fieldName, validator.field(fieldName).id, res.result, res.helpText)
    }

  def updateControlGroupClassAndHelpMessage(fieldName: String, id: String, validation: ValidationResult, helpText: String): net.liftweb.http.js.JsCmd =
    validation match {
      case ValidationResult.None ⇒ Call("updateValidation", fieldName, id, false, false, false, helpText)
      case ValidationResult.Warning ⇒ Call("updateValidation", fieldName, id, false, true, false, helpText)
      case ValidationResult.Error ⇒ Call("updateValidation", fieldName, id, true, false, false, helpText)
      case ValidationResult.Success ⇒ Call("updateValidation", fieldName, id, false, false, true, helpText)
    }
}