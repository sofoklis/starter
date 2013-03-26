package com.papasofokli.util.common

import com.papasofokli.model.shipmanagement.Rank
import com.papasofokli.model.shipmanagement.ShipmanagementSchema

/**
 * Table implementation to help create the Datatable using a javascript arrays, still in progress
 */
case class Header(title: String)

trait CellValue {
  def value: String
}

trait EditableCellValue extends CellValue {
  def updateValue(newValue: String)
}

case class Column(header: Header, columnValue: Int => CellValue)

case class Table[T](columns: List[Column], dataPager: (Int, Int) => List[T]) {

  //def getRows(numberOfRecords: Option[Int], page: Option[Int]): List[T]
}

object Table {
  import com.papasofokli.model.full.ImplicitVal._
  import com.papasofokli.model.full.FullDatabaseSchema._
  import com.papasofokli.model.common.Person
  import org.squeryl._
  import com.papasofokli.model.common.CommonSchema

  import org.squeryl.PrimitiveTypeMode._

  def searchForPeople(name: String, offset: Int, pageLength: Int)(implicit schema: CommonSchema) =
    inTransaction {
      from(schema.person)(p =>
        where(p.firstName like name)
          select (p)
          orderBy (p.firstName asc)).page(offset, pageLength)
    }

  def main(args: Array[String]) {
    initH2Session
    //val dataPager 
    //val col1 = Column(Header("Name"),  )

    //val data = Table[Rank]()
  }
}