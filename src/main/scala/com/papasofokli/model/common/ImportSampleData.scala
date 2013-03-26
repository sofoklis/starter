package com.papasofokli.model.common
import java.util.Date
import org.squeryl.PrimitiveTypeMode._
import java.util.Calendar
import java.util.GregorianCalendar
import scala.util.Random
import net.liftweb.common.Loggable

object ImportSampleData extends Loggable {

  def generateRandomDate: Date = {
    // Get a new random instance, seeded from the clock
    val calendar = Calendar.getInstance
    calendar.set(1920, Calendar.JANUARY, 1)
    val date1920 = calendar.getTime()
    // Get an Epoch value roughly between 1940 and 2010
    // -946771200000L = January 1, 1940
    // Add up to 70 years to it (using modulus on the next long)
    val ms: Long = date1920.getTime + (math.random * (80L * 365 * 24 * 60 * 60 * 1000)).toLong
    new Date(ms)
  }

  def loadData(implicit schema: CommonSchema) {
    logger.info("importing common setup data")
    ImportCountries
    addStateProvince
    addPerson
    addContactInfoType
    addContactInfo
    addAddress
  }

  def ImportCountries(implicit schema: CommonSchema) {
    object IsoCountry {
      def apply(name: String, code: String) = name + ";" + code
      def unapply(str: String): Option[(String, String)] = {
        val parts = str split ";"
        if (parts.length == 2) Some(parts(0), parts(1)) else None
      }
    }
    val sourceFolder: String = System.getenv("PROJECTFOLDER") + "/sampledata/common"

    val filePath = sourceFolder + "/" + "list-en1-semic-3.txt"
    import scala.io.Source._
    val lines = fromFile(filePath).getLines

    lines.foreach(line ⇒ line match {
      case IsoCountry(name, code) ⇒ schema.country.insert(Country(0, code, name))
      case _ ⇒ println(line)
    })

  }

  def addPerson(implicit schema: CommonSchema) {
    object P {
      def apply(firstName: String, lastName: String) = firstName + "," + lastName
      def unapply(str: String): Option[(String, String)] = {
        val parts = str split ","
        if (parts.length > 1) Some(parts(0), parts(1)) else None
      }
    }
    val sourceFolder: String = System.getenv("PROJECTFOLDER") + "/sampledata/common"

    val filePath = sourceFolder + "/" + "randomNames.csv"
    import scala.io.Source._
    val lines = fromFile(filePath).getLines
    //logger.info(s"importing ${lines.length} persons")

    schema.person.insert(Person("Sofoklis", None, "Papasofokli", Gender.Male, generateRandomDate))
    lines.foreach(line ⇒ line match {
      case P(fn, ln) ⇒ { logger.info(s"$fn"); schema.person.insert(Person(fn, None, ln, Gender.Male, generateRandomDate)) }
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