package com.papasofokli.model.shipmanagement
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

case class Rank(id: Long, name: String) extends KeyedEntity[Long] {
  def this(rankName: String) = this(0, rankName)

  def rankGroups(implicit schema: ShipmanagementSchema) = schema.rankRankGroupMapping.left(this)
}

object Rank {
  def apply(name: String) = new Rank(name)

  def getAll(implicit schema: ShipmanagementSchema) = inTransaction { schema.rank.seq }
}