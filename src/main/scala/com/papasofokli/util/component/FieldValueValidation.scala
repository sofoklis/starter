package com.papasofokli.util.component

import com.papasofokli.util.component.ValidationResult._
import net.liftweb.http.RequestVar
import scala.collection.mutable.Map

class FieldValueValidationMap {
  import scala.collection.mutable.Map
  private val valueMap = Map[String, String]()
  private val validationMap = Map[String, ValidationHelpText]()

  def value(fieldName: String): String = valueMap.getOrElse(fieldName, "")
  def validation(fieldName: String): ValidationHelpText = validationMap.getOrElse(fieldName, ValidationHelpText(ValidationResult.None, ""))
  def apply(fieldName: String): String = value(fieldName)

  def setValue(validator: FieldValidator)(fieldName: String)(value: String) {
    valueMap += fieldName -> value
    validationMap += fieldName -> validator.field(fieldName).validateFunction()
  }

  def printElements = println(valueMap)

  def valueExists = valueMap.exists _
  def validationExists = validationMap.exists _
}

object FieldValueValidationMap {
  object vvMap extends RequestVar[FieldValueValidationMap](new FieldValueValidationMap())
}