package com.papasofokli.snippet.bootstrap
import com.papasofokli.model.common.ContactInfo
import net.liftweb.http.SHtml
import net.liftweb.http.js.JE.JsRaw
import com.papasofokli.util.component.ValidationHelpText
import com.papasofokli.util.component.ValidationResult
import com.papasofokli.util.component.BootstrapFieldValidator._
import net.liftweb.http.js.JE.Call
import net.liftweb.http._
import S._
import js.JsCmds._
import com.papasofokli.model.common.ContactInfoType
import net.liftweb.http.js.JsCmd
import net.liftweb.util.Bindable
import net.liftweb.http.S
import net.liftweb.util.BindHelpers._
import net.liftweb.common.Full
import scala.xml.Text
import net.liftweb.common.Box
import scala.xml.NodeSeq
import ContactInfoControl._
import com.papasofokli.util.component.ValidationResult
class ContactInfoTemp(var cType: String, var number: String, var deleted: Boolean,
  val contactInfo: Option[ContactInfo], val id: String)

class ContactInfoControl(contactTypes: List[com.papasofokli.model.common.ContactInfoType],
  temp: ContactInfoTemp) {
  val contactInfo = "contactInfo"

  def deleteUndoCallback(value: String): net.liftweb.http.js.JsCmd = {
    println("delete" + value)
    if (temp.deleted == false) {
      temp.deleted = true
      val res = ValidationHelpText(ValidationResult.Success, "Deleted")
      updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText) &
        Call("contactInfoDeleteUpdate", temp.id, true)
    } else {
      temp.deleted = false
      val res = validateContactTypeNumber(contactTypes)(temp)
      updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText) &
        Call("contactInfoDeleteUpdate", temp.id, false)
    }
  }

  def render = {
    val aCall = SHtml.ajaxCall(JsRaw(""), deleteUndoCallback _)

    "#contactNumberEdit" #> SHtml.ajaxText(temp.number, inputCallback(temp) _, "autocomplete" -> "off") &
      "#contactTypeSelect" #> SHtml.ajaxSelect(contactTypes.map(t ⇒ (t.name, t.name)), Full(temp.cType), selectCallback _) &
      "#contactInfoHelp [id]" #> (contactInfo + "Help" + temp.id) &
      "#contactInfoControl [id]" #> (contactInfo + "Control" + temp.id) &
      "#contactInfoDeleteButton [onclick]" #> Text(aCall.exp.toJsCmd) &
      "#contactInfoDeleteButton [id]" #> (contactInfo + "DeleteButton" + temp.id)
  }

  def selectCallback(value: String): JsCmd = {
    temp.cType = value
    println(value)
    val res = validateContactTypeNumber(contactTypes)(temp)
    updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText)
  }

  def deleteUndoCallback(temp: ContactInfoTemp)(value: String): JsCmd = {
    println("delete" + value)
    if (temp.deleted == false) {
      temp.deleted = true
      val res = ValidationHelpText(ValidationResult.Success, "Deleted")
      updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText) &
        Call("contactInfoDeleteUpdate", temp.id, true)
    } else {
      temp.deleted = false
      val res = validateContactTypeNumber(contactTypes)(temp)
      updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText) &
        Call("contactInfoDeleteUpdate", temp.id, false)
    }
  }

  def inputCallback(temp: ContactInfoTemp)(value: String): JsCmd = {
    println(value)
    temp.number = value
    val res = validateContactTypeNumber(contactTypes)(temp)
    updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText)
  }

}

object ContactInfoControl {
  def render(contactTypes: List[com.papasofokli.model.common.ContactInfoType],
    temp: ContactInfoTemp): Box[NodeSeq] = {
    S.runTemplate(
      "templates-hidden" :: "component" :: "contactInfoControl" :: Nil,
      "component/ContactInfoControl" -> (new ContactInfoControl(contactTypes, temp).render(_)))
  }

  def validateContactTypeNumber(contactTypes: List[com.papasofokli.model.common.ContactInfoType])(temp: ContactInfoTemp): ValidationHelpText =
    contactTypes.find(_.name == temp.cType) match {
      case None ⇒ new ValidationHelpText(ValidationResult.Error, "Type " + temp.cType + "does not exist")
      case Some(ct) ⇒ validateContactNumber(ct, temp)
    }

  def validateContactNumber(cType: ContactInfoType, temp: ContactInfoTemp): ValidationHelpText =
    if (!temp.contactInfo.isEmpty && cType.name == temp.cType && temp.contactInfo.get.contactText == temp.number)
      new ValidationHelpText(ValidationResult.None, "")
    else
      cType.regularExpression match {
        case None ⇒ numberRequired(temp.number)
        case Some(re) ⇒ new ValidationHelpText(ValidationResult.Error, "Validate me!")
      }

  def numberRequired(number: String): ValidationHelpText = number match {
    case null ⇒ new ValidationHelpText(ValidationResult.Error, "Contact number/text required")
    case any ⇒ any.trim match {
      case "" ⇒ new ValidationHelpText(ValidationResult.Error, "Contact number/text required")
      case _ ⇒ new ValidationHelpText(ValidationResult.Success, "Changed")
    }
  }
}