package com.papasofokli.model.shipmanagement
import org.squeryl._

case class RankGroup(id: Long, name: String) extends KeyedEntity[Long] {
  def this(rankGroupName: String) = this(0, rankGroupName)

  def ranks(implicit schema: ShipmanagementSchema) = schema.rankRankGroupMapping.right(this)
}

object RankGroup {
  def apply(name: String) = new RankGroup(name)
}