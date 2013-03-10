package com.papasofokli.model.shipmanagement
import org.squeryl._
case class Seaman(
  id: Long,
  personId: Long,
  rankId: Long) extends KeyedEntity[Long] {

  def this(pId: Long, rId: Long) = this(0, pId, rId)

}