package com.papasofokli.util.component
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JE.Call
import net.liftweb.http._
import net.liftweb.http.S._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import com.papasofokli.util.component.StandardFields._
import net.liftweb.http.js.JsExp.boolToJsExp
import net.liftweb.http.js.JsExp.strToJsExp

object PersonalIfnoFieldValidator extends FieldValidator {
  import com.papasofokli.util.component.StandardFields._
  val fieldNameIdCallbackMap = Map(firstName -> StandardFields.firstNameField(this),
    middleName -> middleNameField(this), lastName -> lastNameField(this),
    dateOfBirth -> dateOfBirthField(this))

  val selectNameIdCallbackMap: Map[String, SelectField] = Map(
    gender -> genderField(this))

  override def ajaxResponse(): JsCmd = {
    var disabled = FieldValueValidationMap.vvMap.validationExists(_._2.result == ValidationResult.Error)
    Call("buttonEnableDisable", "saveChanges", disabled)
  }

}