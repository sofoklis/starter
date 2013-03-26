package com.papasofokli.util.component

object ValidationResult extends Enumeration {
  type ValidationResult = Value
  val None = Value(0, "")
  val Success = Value(1, "success")
  val Warning = Value(2, "warning")
  val Error = Value(3, "error")
}

import com.papasofokli.util.component.ValidationResult._

case class ValidationHelpText(result: ValidationResult, helpText: String)

object ValidationHelpText {

  def required(value: String): ValidationHelpText = value match {
    case null ⇒ ValidationHelpText(ValidationResult.Error, "Required")
    case _ ⇒ value.trim match {
      case "" ⇒ ValidationHelpText(ValidationResult.Error, "Required")
      case _ ⇒ ValidationHelpText(ValidationResult.None, "")
    }
  }

  def isDate(value: String): ValidationHelpText = {
    val stringFormat = "dd-MM-yyyy"
    val format = new java.text.SimpleDateFormat(stringFormat)
    format.setLenient(false)
    try {
      format.parse(value)
      ValidationHelpText(ValidationResult.None, "")
    } catch {
      case _: Exception ⇒ ValidationHelpText(ValidationResult.Error, value + " is not a valid date (" + stringFormat + ").")
    }
  }

  def integer(value: String): ValidationHelpText =
    try {
      value.toInt
      ValidationHelpText(ValidationResult.None, "")
    } catch {
      case _: java.lang.NumberFormatException ⇒ ValidationHelpText(ValidationResult.Error, value + " is not an integer value")
    }

  def minLength(length: Int)(value: String): ValidationHelpText =
    if (value.trim.length < length) ValidationHelpText(ValidationResult.Error, "Required mininum length " + length)
    else ValidationHelpText(ValidationResult.None, "")

  def maxLength(length: Int)(value: String): ValidationHelpText =
    if (value.trim.length > length) ValidationHelpText(ValidationResult.Error, "Maxinum length " + length)
    else ValidationHelpText(ValidationResult.None, "")

  def allGood: ValidationHelpText = ValidationHelpText(None, "")

  def valueInList(validValueList: List[String])(value: String): ValidationHelpText =
    if (validValueList.contains(value))
      ValidationHelpText(None, "")
    else
      ValidationHelpText(Error, value + " is not a valid value.")
}

