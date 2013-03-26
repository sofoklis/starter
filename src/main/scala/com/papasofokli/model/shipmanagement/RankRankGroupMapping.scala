package com.papasofokli.model.shipmanagement
import org.squeryl.KeyedEntity
import org.squeryl.dsl.CompositeKey2
import org.squeryl.PrimitiveTypeMode._

case class RankRankGroupMapping(rankId: Long, rankGroupId: Long) extends KeyedEntity[CompositeKey2[Long, Long]] {
  def id = compositeKey(rankId, rankGroupId)
}
