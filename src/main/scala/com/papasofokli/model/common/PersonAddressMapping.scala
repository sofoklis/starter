package com.papasofokli.model.common
import org.squeryl.KeyedEntity
import org.squeryl.dsl.CompositeKey2
import org.squeryl.dsl.CompositeKey2._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{ Query, Session, KeyedEntity, Schema }
import org.squeryl.dsl.GroupWithMeasures

case class PersonAddressMapping(personId : Long, addressId : Long) extends KeyedEntity[CompositeKey2[Long, Long]] {
  def id = compositeKey(personId, addressId)
}