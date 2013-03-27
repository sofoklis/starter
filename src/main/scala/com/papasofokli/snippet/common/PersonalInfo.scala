package com.papasofokli.snippet.common
import net.liftweb.common.Loggable
import net.liftweb.http.RequestVar
import com.papasofokli.model.common.Person
import com.papasofokli.model.full.ImplicitVal._
import net.liftweb.util.CssSel
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.util.Bindable
import net.liftweb.http.S
import net.liftweb.util.BindHelpers._
import net.liftweb.http._
import com.papasofokli.util.common.Calculations
import com.papasofokli.model.common.Gender
import net.liftweb.common.Full
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.js.JsCmds.Run
import reactive._
import web._
import html._
import scala.xml.NodeSeq
import scala.xml.Text
import com.papasofokli.util.component.{ FieldValidator, BootstrapFieldValidator, ValidationResult, ValidationHelpText }
import com.papasofokli.snippet.component.{ Input, Control }
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JE.Call
import net.liftweb.http._
import S._
import js.JsCmds._
import com.papasofokli.util.component._
import java.text.SimpleDateFormat
import net.liftweb.util.PassThru

class PersonalInfo extends Loggable with Observing {

  def validateAllACall: (String, net.liftweb.http.js.JsExp) =
    SHtml.ajaxCall(JsRaw(""), validateAllCallback)

  def validateAllCallback(value: String): JsCmd = PersonalIfnoFieldValidator.ajaxUpdateAll
  val format = new SimpleDateFormat("dd-MM-yyyy")
  format.setLenient(false)

  def render = {
    S.appendJs(modalscript)
    "#personalInfo" #> SHtml.idMemoize(pi ⇒
      {
        logger.info(PersonalInfo.PersonOption)
        Control.Validator(PersonalIfnoFieldValidator)
        val person = PersonalInfo.PersonOption.getOrElse(inTransaction { commonSchema.person.lookup(1l).get })

        PersonalIfnoFieldValidator.setValue(StandardFields.firstName, person.firstName);
        PersonalIfnoFieldValidator.setValue(StandardFields.middleName, person.middleName.getOrElse(""));
        PersonalIfnoFieldValidator.setValue(StandardFields.lastName, person.lastName);
        PersonalIfnoFieldValidator.setValue(StandardFields.dateOfBirth, format.format(person.dateOfBirth));
        PersonalIfnoFieldValidator.setValue(StandardFields.gender, person.gender.toString);

        FieldValueValidationMap.vvMap.printElements
        PersonalInfo.PersonOption(Some(person))

        ContactInfoView.PersonOption(Some(person))
        AddressView.PersonOption(Some(person))

        lazy val aCall: (String, net.liftweb.http.js.JsExp) =
          SHtml.ajaxCall(JsRaw(""), saveChanges(person)(pi))

        "#name *" #> (person.firstName + " " + person.lastName) &
          (if (Calculations.aniversary(person.dateOfBirth)) "#wishes *" #> "Happy Birthday"
          else "#wishes" #> "") &
          "#firstName *" #> person.firstName &
          "#middleName *" #> middleNameCss(person.middleName) &
          "#lastName *" #> person.lastName &
          "#gender *" #> person.gender.toString &
          "#age *" #> Calculations.calculateAge(person.dateOfBirth).toString &
          "#dob *" #> format.format(person.dateOfBirth) &
          "#saveChanges [onClick]" #> aCall._2.toJsCmd
      })
  }

  def middleNameCss(middleName: Option[String]) = middleName match {
    case None => "-"
    case Some(mn) => mn
  }

  def modalscript = JsRaw("""
    	$(function() {
   			$('#personalInfoEditModal').on('shown', function () {
    			""" + validateAllACall._2.toJsCmd + """
    		});
    		$('#personalInfoEditModal').on('hide', function () {
    			//alert("hide");
    		})
    	});
    	""")

  def saveChanges(person: Person)(pi: net.liftweb.http.IdMemoizeTransform)(value: String): net.liftweb.http.js.JsCmd = {
    val v = PersonalIfnoFieldValidator

    val fn = v.value(StandardFields.firstName).trim
    val mn = v.value(StandardFields.middleName).trim
    val ln = v.value(StandardFields.lastName).trim
    val dob = v.value(StandardFields.dateOfBirth).trim
    val gen = Gender.values.find(_.toString == v.value(StandardFields.gender).trim)

    val p = person.copy(firstName = fn, lastName = ln,
      middleName = if (mn == "") None else Some(mn),
      dateOfBirth = format.parse(dob), gender = gen.getOrElse(Gender.Male))
    p.update
    PersonalInfo.PersonOption(Some(p))
    JsRaw("$('#personalInfoEditModal').modal('hide')") & SHtml.ajaxInvoke(pi.setHtml _)
  }
}

object PersonalInfo {
  object PersonOption extends SessionVar[Option[Person]](None)
}

