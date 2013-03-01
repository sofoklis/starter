package com.papasofokli.model.common
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

case class ContactInfoType(id : Long,
                           name : String,
                           abbreviation : Option[String],
                           regularExpression : Option[String],
                           regularExpressionDescription : Option[String]) extends KeyedEntity[Long] {
  def this() = this(0, "", Some(""), Some(""), Some(""))
}

object ContactInfoType {
  def allTypes(implicit schema : CommonSchema) : List[ContactInfoType] = inTransaction { schema.contactInfoType.where(_.id gt 0).toList }
}

case class ContactInfo(id : Long, typeId : Long, contactText : String) extends KeyedEntity[Long] {
  def contactInfoType(implicit schema : CommonSchema) = inTransaction { schema.contactInfoType.lookup(this.typeId).get }

  def this(typeId : Long, contactText : String) = this(0, typeId, contactText)
}

object ContactInfo {
  def insert(ci : ContactInfo)(implicit schema : CommonSchema) = inTransaction {
    schema.contactInfo.insert(ci)
  }

  def update(ci : ContactInfo)(implicit schema : CommonSchema) = inTransaction {
    println("update " + ci)
    schema.contactInfo.update(ci)
  }

  def delete(ci : ContactInfo)(implicit schema : CommonSchema) = inTransaction {
    schema.personContactInfoMapping.deleteWhere(mapp ⇒ mapp.contactInfoId === ci.id)
    schema.contactInfo.deleteWhere(i ⇒ ci.id === i.id)
  }

}