package com.papasofokli.model.common
import org.squeryl.PrimitiveTypeMode._

/**
 * Table used to order elements from other tables.
 * sequence: is the weight for the sorting
 * tableName: elements for the sorting
 * elementId: the table row id for the particular element
 */
class Sequence private (
  val sequence: Double,
  val tableName: String,
  val elementId: Long) {

  override def toString() = "Sequence: " + sequence + " " + tableName + " " + elementId
}

object Sequence {
  def apply(sequence: Double, element: OrderedTable) =
    {
      new Sequence(sequence, element.tableName, element.id)
    }

  def insert(s: Sequence)(implicit schema: CommonSchema) = inTransaction {
    schema.sequence.insert(s)
    UserActivityLog.insertLog(1, 1 + " new " + s)
  }

  def deleteAll(tableName: String)(implicit schema: CommonSchema) {
    inTransaction { schema.sequence.deleteWhere(s ⇒ s.tableName === tableName) }
  }

  def sequences(implicit schema: CommonSchema) =
    inTransaction { from(schema.sequence)(s ⇒ where(true === true) select s orderBy (s.tableName, s.sequence, s.elementId)).toList }
}