package com.papasofokli.model.shipmanagement
import org.squeryl._
case class Rank(id: Long, name: String) extends KeyedEntity[Long] {
  def this(rankName: String) = this(0, rankName)
}