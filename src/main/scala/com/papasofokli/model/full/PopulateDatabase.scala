package com.papasofokli.model.full
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.common.Logger
import com.papasofokli.model.full.ImplicitVal._
import com.papasofokli.model.common._
import com.papasofokli.util.security.Authentication
import net.liftweb.common.Loggable

object PopulateDatabase extends Loggable {

  def populateDb =
    {
      logger.info("populateDb")

      ImportSampleData.loadData

      addUsers
    }
  private def addUsers {

    // Add some users
    User.insert(User("sofoklis24@gmail.com", Authentication.encrypt("password24")))
    User.insert(User("s@g.com", Authentication.encrypt("p")))
    User.insert(User("sofoklis25@gmail.com", Authentication.encrypt("password25")))
    User.insert(User("sofoklis26@gmail.com", Authentication.encrypt("password26")))
    User.insert(User("sofoklis27@gmail.com", Authentication.encrypt("password")))
    User.insert(User("sofoklis28@gmail.com", Authentication.encrypt("password")))
    User.insert(User("sofoklis29@gmail.com", Authentication.encrypt("password")))
    User.insert(User("sofoklis30@gmail.com", Authentication.encrypt("password")))
    User.insert(User("leshi@gmail.com", Authentication.encrypt("leshi")))
    User.insert(User("mikis@gmail.com", Authentication.encrypt("mikis")))
    User.insert(User("raivo@gmail.com", Authentication.encrypt("mf")))
  }

}