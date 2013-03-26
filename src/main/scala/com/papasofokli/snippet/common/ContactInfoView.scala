package com.papasofokli.snippet.common
import net.liftweb.http.RequestVar
import com.papasofokli.model.common.Person
import com.papasofokli.model.full.ImplicitVal._
import net.liftweb.util.CssSel
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.util.Bindable
import net.liftweb.http.S
import net.liftweb.util.BindHelpers._
import com.papasofokli.model.common.ContactInfoType
import net.liftweb.util.Helpers
import scala.xml.Text
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JE.Call
import net.liftweb.http._
import S._
import js.JsCmds._
import net.liftweb.common._
import com.papasofokli.snippet.bootstrap.ContactInfoTemp
import com.papasofokli.snippet.bootstrap.ContactInfoControl
import com.papasofokli.model.common.ContactInfo
import com.papasofokli.util.component._
import com.papasofokli.util.component.BootstrapFieldValidator._
class ContactInfoView {
  lazy val contactTypes = ContactInfoType.allTypes
  val contactInfo = "contactInfo"

  var cInfo: net.liftweb.http.IdMemoizeTransform = null
  var fieldSet: net.liftweb.http.IdMemoizeTransform = null

  var contactInfoTempList = List[ContactInfoTemp]()

  /**
   * The contact info list in view mode
   */
  def contactList = "#contactList" #> SHtml.idMemoize(ci ⇒ {
    contactInfoTempList = createContactInfoTempList
    "#contactLi *" #> contactInfoTempList.map(con ⇒
      {
        cInfo = ci
        var contactType = contactTypes.find(_.id == con.contactInfo.get.typeId).get
        "#contactType [title]" #> contactType.name &
          "#contactType *" #> contactType.abbreviation.getOrElse(contactType.name) &
          "#contactNumber *" #> con.number
      })
  })

  /**
   * Uses the control to list the editable contact information
   */
  val contactEditList = "#contactInfoFieldSet" #> SHtml.idMemoize(eNe ⇒ {
    fieldSet = eNe
    val aCall = SHtml.ajaxCall(JsRaw(""), addContactCallback(eNe) _)
    "#existingNumbersEdit *" #> contactInfoTempList.map(con ⇒ ContactInfoControl.render(contactTypes, con).get) &
      "#addContactButton [onClick]" #> Text(aCall.exp.toJsCmd)
  })

  def addContactCallback(ci: net.liftweb.http.IdMemoizeTransform)(value: String): JsCmd = {
    val id = Helpers.nextFuncName
    contactInfoTempList = contactInfoTempList ::: new ContactInfoTemp(contactTypes.head.name, "", false, None, id) :: Nil

    SHtml.ajaxInvoke(() ⇒ ci.setHtml() & contactInfoTempList.map(temp ⇒ {
      if (temp.deleted) {
        val res = ValidationHelpText(ValidationResult.Success, "Deleted")
        updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText) &
          Call("contactInfoDeleteUpdate", temp.id, true)
      } else {
        val res = ContactInfoControl.validateContactTypeNumber(contactTypes)(temp)
        updateControlGroupClassAndHelpMessage(contactInfo, temp.id, res.result, res.helpText)
      }
    }))
  }

  def createContactInfoTempList = ContactInfoView.PersonOption.get match {
    case None ⇒ List[ContactInfoTemp]()
    case Some(person) ⇒ inTransaction {
      person.contactInfo.toList.map(ci ⇒ {
        val id = Helpers.nextFuncName
        var contactType = contactTypes.find(_.id == ci.typeId).get
        new ContactInfoTemp(contactType.name, ci.contactText, false, Some(ci), id)
      })
    }
  }

  def saveCallback(value: String): JsCmd = {
    val validationList = contactInfoTempList.map(temp ⇒ ContactInfoControl.validateContactTypeNumber(contactTypes)(temp))
    if (validationList.exists(_.result == ValidationResult.Error)) {
      // Should not allow to save, and show warning
      Noop
    } else {
      inTransaction {
        contactInfoTempList.foreach(saveContactInfo(_))
      }
      SHtml.ajaxInvoke(() ⇒ cInfo.setHtml() & fieldSet.setHtml())
    }
  }

  def saveContactInfo(temp: ContactInfoTemp) {
    val ct = contactTypes.find(_.name == temp.cType).get

    temp.contactInfo match {
      case None ⇒ {
        if (!temp.deleted)
          ContactInfoView.PersonOption.get match {
            case Some(p) ⇒ {
              val ci = ContactInfo.insert(new ContactInfo(ct.id, temp.number))
              p.contactInfo.associate(ci)
            }
            case None ⇒ println("no person nothing to do")
          }
      }
      case Some(ci) ⇒
        if (!temp.deleted) {
          if (ci.contactText != temp.number.trim || ct.id != ci.typeId) {
            val cInfo = ci.copy(contactText = temp.number.trim, typeId = ct.id)
            ContactInfo.update(cInfo)
          }
        } else ContactInfo.delete(ci)
    }
  }

  def render: CssSel =
    contactList & contactEditList &
      "#saveChanges [onClick]" #> {
        val saveACall = SHtml.ajaxCall(JsRaw(""), saveCallback _)
        Text(saveACall.exp.toJsCmd)
      }

}

object ContactInfoView {
  object PersonOption extends RequestVar[Option[Person]](None)
}