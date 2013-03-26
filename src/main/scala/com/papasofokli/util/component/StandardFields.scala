package com.papasofokli.util.component
import net.liftweb.http._
import net.liftweb.http.S._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import com.papasofokli.util.component.BootstrapFieldValidator._
import com.papasofokli.util.component.ValidationHelpText._
import com.papasofokli.model.common.Gender

object StandardFields {
  val firstName = "firstName"
  def firstNameField(validator: FieldValidator) = InputField(firstName, validator.getNextId, "First Name",
    () ⇒ minLength(3)(validator.value(firstName)), updateControlGroupClassAndHelpMessage(validator)(firstName))

  val middleName = "middleName"
  def middleNameField(validator: FieldValidator) = InputField(middleName, validator.getNextId, "Middle Name",
    () ⇒ allGood, updateControlGroupClassAndHelpMessage(validator)(middleName))

  val lastName = "lastName"
  def lastNameField(validator: FieldValidator) = InputField(lastName, validator.getNextId, "Last Name",
    () ⇒ minLength(3)(validator.value(lastName)), updateControlGroupClassAndHelpMessage(validator)(lastName))

  val dateOfBirth = "dateOfBirth"
  def dateOfBirthField(validator: FieldValidator) = InputField(dateOfBirth, validator.getNextId, "Date Of Birth",
    () ⇒ isDate(validator.value(dateOfBirth)), updateControlGroupClassAndHelpMessage(validator)(dateOfBirth))

  val gender = "gender"
  def genderField(validator: FieldValidator) = SelectField(gender, validator.getNextId, "Gender",
    () ⇒ valueInList(Gender.values.map(_.toString).toList)(validator.value(gender)),
    updateControlGroupClassAndHelpMessage(validator)(gender), Gender.values.map(_.toString).toList)

}