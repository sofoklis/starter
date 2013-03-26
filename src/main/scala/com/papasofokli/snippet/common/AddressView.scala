package com.papasofokli.snippet.common
import com.papasofokli.model.common.Address
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.util.BindHelpers._
import net.liftweb.http._
import scala.xml.NodeSeq
import net.liftweb.util.CssSel
import net.liftweb.util.Bindable
import net.liftweb.http.S
import net.liftweb.common.Loggable
import org.squeryl._
import com.papasofokli.model.common.Person

class AddressView extends Loggable {
  import com.papasofokli.model.full.ImplicitVal
  implicit val schema = ImplicitVal.commonSchema

  val addressList: List[(Address, Int)] = inTransaction {
    (schema.person.lookup(1l).get.addresses.toList ::: schema.person.lookup(2l).get.addresses.toList) zipWithIndex
  }

  def render: CssSel =
    {

      "#addressTitle" #> addressList.map(address ⇒
        ("#addressTitle [class+]" #> (if (address._2 == 0) "active" else "") &
          "#addressLink [href]" #> ("#Address" + address._2) &
          "#addressLink *" #> address._1.title.getOrElse("Address"))) &
        "#address" #> addressList.map(address ⇒
          {
            val province = address._1.stateProvince
            val country = province.country

            "#address [class+]" #> (if (address._2 == 0) "active" else "") &
              "#address [id]" #> ("Address" + address._2) &
              "#title *" #> address._1.title.getOrElse("Address") &
              "#line1" #> address._1.line1 &
              "#line2" #> address._1.line2.getOrElse("") &
              "#stateProvince" #> province.name &
              "#countryZip" #> (country.name.capitalize + " " + address._1.zipCode.getOrElse("")) &
              "#editButton [onClick]" #> "Edit" &
              "#deleteButton [onClick]" #> "Delete"
          })
    }
}

object AddressView {
  object addressList extends RequestVar[List[Address]](List[Address]())
  object PersonOption extends RequestVar[Option[Person]](None)
}