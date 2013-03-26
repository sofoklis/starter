package com.papasofokli.model.common
import org.squeryl.KeyedEntity
import org.squeryl.dsl.CompositeKey2
import org.squeryl.PrimitiveTypeMode._

case class PersonContactInfoMapping(personId: Long, contactInfoId: Long) extends KeyedEntity[CompositeKey2[Long, Long]] {
  def id = compositeKey(personId, contactInfoId)
}
