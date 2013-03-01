package com.papasofokli.model.common
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

case class Country(id : Int, twoDigitCode : String, name : String) extends KeyedEntity[Int]

case class StateProvince(id : Long, countryId : Int, name : String) extends KeyedEntity[Long] {
  def country(implicit schema : CommonSchema) = inTransaction { schema.country.lookup(this.countryId).get }
}

case class Address(id : Long, title : Option[String], line1 : String, line2 : Option[String], zipCode : Option[String], stateProvinceId : Long)
  extends KeyedEntity[Long] {
  def this() = this(1, Some(""), "", Some(""), Some(""), 1)

  def stateProvince(implicit schema : CommonSchema) = inTransaction { schema.stateProvince.lookup(this.stateProvinceId).get }
  def people(implicit schema : CommonSchema) = schema.personAddressMapping.right(this)
}

