package com.papasofokli.model.shipmanagement
import org.squeryl.PrimitiveTypeMode._
import org.squeryl._
import com.papasofokli.model.common.CommonSchema

/**
 * Schema that builds on common schema
 */
trait ShipmanagementSchema {
  self: CommonSchema =>
  val ship = table[Ship]
  val seaman = table[Seaman]
  val rank = table[Rank]

  on(ship)(s ⇒ declare(s.id is (autoIncremented, indexed, unique, primaryKey)))

  on(rank)(r ⇒ declare(
    r.id is (autoIncremented, indexed, unique, primaryKey),
    r.name is (indexed, unique)))

  on(seaman)(s ⇒ declare(
    s.id is (autoIncremented, indexed, unique, primaryKey),
    s.personId is (indexed, unique),
    s.rankId is indexed))

  //val seamanPersonMapping =
  //  oneToOneRelation(seaman, person).
  //    via[PersonContactInfoMapping]((p, ci, pci) ⇒ (pci.personId === p.id, pci.contactInfoId === ci.id))

}