package com.papasofokli.model.full
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.common.Logger
import com.papasofokli.model.full.ImplicitVal._
import com.papasofokli.model.common._

object PopulateDatabase {

  def populateDb =
    {
      Logger("populateDb")

      ImportSampleData.loadData

      addUsers
    }
  private def addUsers {

    // Add some users
    User.insert(User("sofoklis24@gmail.com", "passowrd"))
    User.insert(User("sofoklis25@gmail.com", "passowrd"))
    User.insert(User("sofoklis26@gmail.com", "passowrd"))
    User.insert(User("sofoklis27@gmail.com", "passowrd"))
    User.insert(User("sofoklis28@gmail.com", "passowrd"))
    User.insert(User("sofoklis29@gmail.com", "passowrd"))
    User.insert(User("sofoklis30@gmail.com", "passowrd"))
    User.insert(User("leshi@gmail.com", "passowrd"))
    User.insert(User("mikis@gmail.com", "passowrd"))
  }

}