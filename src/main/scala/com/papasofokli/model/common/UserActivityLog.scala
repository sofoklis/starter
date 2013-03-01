package com.papasofokli.model.common
import org.squeryl._
import org.squeryl.annotations.Column
import org.squeryl.PrimitiveTypeMode._
import java.sql.Timestamp
import net.liftweb.common.Loggable

case class UserActivityLog(
  val id: Long,
  val userId: Long,
  @Column(length = 1024) val activity: String,
  val timestamp: Timestamp) extends KeyedEntity[Long] with OrderedTable {
  def name = toString
}

object UserActivityLog extends Loggable {
  def apply(userId: Long, activity: String, timestamp: Timestamp) = new UserActivityLog(0, userId, activity, timestamp)

  def lastLogs(number: Int = 10)(implicit schema: CommonSchema) = inTransaction { from(schema.userActivityLog)(log ⇒ select(log) orderBy (log.timestamp desc)).page(0, number).toList }

  def insertLog(userId: Long, activity: String)(implicit schema: CommonSchema) =
    {
      inTransaction { schema.userActivityLog.insert(UserActivityLog(userId, activity, new java.sql.Timestamp(new java.util.Date().getTime))) }
      //ActivityActor ! ActivityActor.BroadcastActivityLog
    }
}