package com.papasofokli.model.common
import java.util.Date
import org.squeryl.PrimitiveTypeMode._
import java.util.Calendar
import java.util.GregorianCalendar

object ImportSampleData {

  def loadData(implicit schema: CommonSchema) {
    ImportCountries
    addStateProvince
    addPerson
    addContactInfoType
    addContactInfo
    addAddress
  }

  def ImportCountries(implicit schema: CommonSchema) {
    object IsoCountry {
      def apply(user: String, domain: String) = user + "@" + domain
      def unapply(str: String): Option[(String, String)] = {
        val parts = str split ";"
        if (parts.length == 2) Some(parts(0), parts(1)) else None
      }
    }
    val sourceFolder: String = System.getenv("PROJECTFOLDER") + "/setupdata/common"

    val filePath = sourceFolder + "/" + "list-en1-semic-3.txt"
    import scala.io.Source._
    val lines = fromFile(filePath).getLines

    lines.foreach(line ⇒ line match {
      case IsoCountry(name, code) ⇒ { schema.country.insert(Country(0, code, name)) }
      case _ ⇒ println(line)
    })

  }

  def addStateProvince(implicit schema: CommonSchema) {
    val cy = from(schema.country)(c ⇒ where(c.twoDigitCode === "CY") select c orderBy (c.id)).head

    schema.stateProvince.insert(StateProvince(0, cy.id, "Limassol"))
    schema.stateProvince.insert(StateProvince(0, cy.id, "Nicosia"))
    schema.stateProvince.insert(StateProvince(0, cy.id, "Larnaca"))
    schema.stateProvince.insert(StateProvince(0, cy.id, "Paphos"))
    schema.stateProvince.insert(StateProvince(0, cy.id, "Ammohostos"))
    schema.stateProvince.insert(StateProvince(0, cy.id, "Kerinia"))
  }

  def addContactInfoType(implicit schema: CommonSchema) {
    schema.contactInfoType.insert(ContactInfoType(0, "Email", None, None, None))
    schema.contactInfoType.insert(ContactInfoType(0, "Cell", None, None, None))
    schema.contactInfoType.insert(ContactInfoType(0, "Phone", None, None, None))
    schema.contactInfoType.insert(ContactInfoType(0, "Pager", None, None, None))
  }

  def addPerson(implicit schema: CommonSchema) {
    val calendar = Calendar.getInstance
    calendar.set(1981, Calendar.JANUARY, 1)

    schema.person.insert(Person(0, "Sofoklis", None, "Papasofokli", Gender.Male, calendar.getTime()))
    calendar.set(1982, Calendar.JULY, 15)
    schema.person.insert(Person(0, "George", None, "Papasofokli", Gender.Male, calendar.getTime()))
    calendar.set(1987, Calendar.JULY, 17)
    schema.person.insert(Person(0, "Koulla", None, "Papasofokli", Gender.Female, calendar.getTime()))
    calendar.set(1980, Calendar.AUGUST, 15)
    schema.person.insert(Person(0, "Aristidis", None, "Theoharous", Gender.Male, calendar.getTime()))
    calendar.set(1980, Calendar.SEPTEMBER, 29)
    schema.person.insert(Person(0, "Maria", None, "Theoharous", Gender.Female, calendar.getTime()))
    calendar.set(1980, Calendar.AUGUST, 16)
    schema.person.insert(Person(0, "Dimos", None, "Hadjilias", Gender.Male, calendar.getTime()))
    calendar.set(1980, Calendar.OCTOBER, 14)
    schema.person.insert(Person(0, "Michalis", None, "Patsalides", Gender.Male, calendar.getTime()))
  }

  def addContactInfo(implicit schema: CommonSchema) {
    val contactInfoTypes = from(schema.contactInfoType)(cit ⇒ where(cit.id gt 0) select cit orderBy (cit.id)).toList

    def addContactInfoToPerson(p: Person) {
      contactInfoTypes.foreach(cit ⇒ {
        val ci = schema.contactInfo.insert(ContactInfo(0, cit.id, cit.id + cit.name + cit.id + p.id + p.id + ""))
        p.contactInfo(schema).associate(ci)
      })
    }

    val people = from(schema.person)(p ⇒ where(p.id gt 0) select p orderBy (p.id)).toList

    people.foreach(addContactInfoToPerson)

  }

  def addAddress(implicit schema: CommonSchema) {
    def addAddressToPerson(p: Person) {
      val lem = from(schema.stateProvince)(sp ⇒ where(sp.name === "Limassol") select sp).toList.head
      val nic = from(schema.stateProvince)(sp ⇒ where(sp.name === "Nicosia") select sp).toList.head
      val address = schema.address.insert(Address(0, Some("Sample Address"), p.firstName + " 17", None, None, lem.id))
      p.addresses(schema).associate(address)
    }

    val people = from(schema.person)(p ⇒ where(p.id gt 0) select p orderBy (p.id)).toList
    people.foreach(addAddressToPerson)
  }

}