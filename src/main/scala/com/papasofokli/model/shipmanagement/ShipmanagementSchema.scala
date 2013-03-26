package com.papasofokli.model.shipmanagement
import org.squeryl.PrimitiveTypeMode._
import org.squeryl._
import com.papasofokli.model.common.CommonSchema

/**
 * Schema that builds on common schema
 */
trait ShipmanagementSchema extends CommonSchema {
  //self: CommonSchema =>
  val ship = table[Ship]
  val seaman = table[Seaman]
  val rank = table[Rank]
  val rankGroup = table[RankGroup]

  on(ship)(s ⇒ declare(s.id is (autoIncremented, indexed, unique, primaryKey)))

  on(rank)(r ⇒ declare(
    r.id is (autoIncremented, indexed, unique, primaryKey),
    r.name is (indexed, unique)))

  on(rankGroup)(rg ⇒ declare(
    rg.id is (autoIncremented, indexed, unique, primaryKey),
    rg.name is (indexed, unique)))

  on(seaman)(s ⇒ declare(
    s.id is (autoIncremented, indexed, unique, primaryKey),
    s.personId is (indexed, unique),
    s.rankId is indexed))

  val rankRankGroupMapping = manyToManyRelation(rank, rankGroup).
    via[RankRankGroupMapping]((r, rg, rgm) => (rgm.rankId === r.id, rgm.rankGroupId === rg.id))

}