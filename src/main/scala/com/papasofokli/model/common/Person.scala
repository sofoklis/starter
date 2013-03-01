package com.papasofokli.model.common
import java.util.Date
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

object Gender extends Enumeration {
  type Gender = Value
  val Male = Value(0, "Male")
  val Female = Value(1, "Female")
}

import com.papasofokli.model.common.Gender._
case class Person(
  id : Long,
  firstName : String,
  middleName : Option[String],
  lastName : String,
  gender : Gender,
  dateOfBirth : Date) extends KeyedEntity[Long] {

  def this() = this(0, "", Some(""), "", Male, new Date())

  def addresses(implicit schema : CommonSchema) = schema.personAddressMapping.left(this)
  def contactInfo(implicit schema : CommonSchema) = schema.personContactInfoMapping.left(this)

  def update(implicit schema : CommonSchema) = inTransaction { schema.person.update(this) }
}

