package com.papasofokli.model.common

trait NameId {
  def id : Long
  def name : String
}

trait OrderedTable extends Ordered[OrderedTable] with NameId {
  def tableName = this.getClass.toString

  def compare(that : OrderedTable) : Int =
    {
      val tNameSeq = this.tableName compare that.tableName
      if (tNameSeq != 0) {
        val seq = this.sequence compare that.sequence
        if (seq != 0) {
          val nSeq = this.name compare that.name
          if (nSeq != 0) this.id compare that.id else nSeq
        } else seq
      } else tNameSeq
    }

  def sequence = OrderedTable.sequenceMap getOrElse ((this.tableName, this.id), Double.MaxValue)
}

object OrderedTable {
  import scala.collection.Map
  private var tableNameUsedSequenceMap = Map[String, List[Double]]()
  private var sequenceMap = scala.collection.mutable.Map[(String, Long), Double]()

  def refresh(implicit schema : CommonSchema) {
    val seqList = Sequence.sequences
    seqList.foreach(AddSeq)
  }

  private def AddSeq(s : Sequence) {
    var list : List[Double] = tableNameUsedSequenceMap.getOrElse(s.tableName, List())
    list = s.sequence :: list
    list.sortWith((a, b) ⇒ a < b)
    tableNameUsedSequenceMap += s.tableName -> list

    sequenceMap += (s.tableName, s.elementId) -> s.sequence
  }

  def sort[T <: OrderedTable](list : List[T]) : List[T] = list.sortWith(asc)

  val asc = (a : OrderedTable, b : OrderedTable) ⇒ a.sequence < b.sequence
  val desc = (a : OrderedTable, b : OrderedTable) ⇒ a.sequence > b.sequence
}