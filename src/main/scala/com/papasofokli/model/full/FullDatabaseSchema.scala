package com.papasofokli.model.full
import org.squeryl.PrimitiveTypeMode._
import org.squeryl._
import adapters.H2Adapter
import com.papasofokli.model.common.CommonSchema
import net.liftweb.common.Loggable

object FullDatabaseSchema extends Loggable with CommonSchema {

  def recreateDb = inTransaction { drop; create; PopulateDatabase.populateDb }

  def initH2Session = {
    logger.info("Database session created")
    SessionFactory.concreteFactory = Some(() ⇒ Session.create(
      //java.sql.DriverManager.getConnection("jdbc:h2:~/h2db/starterdb"), //;AUTO_SERVER=TRUE"),
      java.sql.DriverManager.getConnection("jdbc:h2:mem"),
      new H2Adapter))
  }
}