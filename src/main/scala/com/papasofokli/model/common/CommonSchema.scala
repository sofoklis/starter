package com.papasofokli.model.common
import org.squeryl.PrimitiveTypeMode._
import org.squeryl._
import adapters.H2Adapter

trait CommonSchema extends Schema {

  val user = table[User]
  val userActivityLog = table[UserActivityLog]

  val person = table[Person]
  val contactInfoType = table[ContactInfoType]
  val contactInfo = table[ContactInfo]

  val country = table[Country]
  val stateProvince = table[StateProvince]
  val address = table[Address]

  val sequence = table[Sequence]

  on(person)(p ⇒ declare(p.id is (autoIncremented, indexed, unique, primaryKey)))

  on(contactInfoType)(cIT ⇒ declare(
    cIT.id is (autoIncremented, indexed, unique, primaryKey),
    cIT.name is (indexed, unique)))

  on(contactInfo)(cI ⇒ declare(
    cI.id is (autoIncremented, indexed, unique, primaryKey),
    cI.typeId is indexed))

  on(user)(u ⇒ declare(
    u.id is (indexed, unique, autoIncremented, primaryKey),
    u.email is (indexed, unique)))

  on(userActivityLog)(ual ⇒ declare(
    ual.id is (indexed, unique, autoIncremented, primaryKey),
    ual.userId is (indexed)))

  on(userActivityLog)(a ⇒ declare(
    a.userId is indexed, a.timestamp is indexed))

  on(country)(c ⇒ declare(c.id is (indexed, unique, autoIncremented, primaryKey),
    c.name is unique,
    c.twoDigitCode is unique))

  on(stateProvince)(sp ⇒ declare(sp.id is (indexed, unique, autoIncremented, primaryKey)))

  on(address)(a ⇒ declare(a.id is (indexed, unique, autoIncremented, primaryKey)))

  on(sequence)(s ⇒ declare(
    s.sequence is indexed,
    s.tableName is indexed,
    s.tableName is indexed))

  val personAddressMapping =
    manyToManyRelation(person, address).
      via[PersonAddressMapping]((p, a, pa) ⇒ (pa.personId === p.id, pa.addressId === a.id))

  val personContactInfoMapping =
    manyToManyRelation(person, contactInfo).
      via[PersonContactInfoMapping]((p, ci, pci) ⇒ (pci.personId === p.id, pci.contactInfoId === ci.id))

  personContactInfoMapping.leftForeignKeyDeclaration.constrainReference(onDelete cascade)
  personContactInfoMapping.rightForeignKeyDeclaration.constrainReference(onDelete cascade)

  personAddressMapping.leftForeignKeyDeclaration.constrainReference(onDelete cascade)
  personAddressMapping.rightForeignKeyDeclaration.constrainReference(onDelete cascade)

}