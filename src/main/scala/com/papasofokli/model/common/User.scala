package com.papasofokli.model.common

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import org.squeryl.annotations.Column

case class User(
  id: Long,
  email: String,
  hashedPasswd: String) extends KeyedEntity[Long] with OrderedTable {

  def name = email

  def this(firstName: String, lastName: String, email: String, password: String) = this(0, email, password)

}

object User {
  def apply(email: String, hashedPassword: String) = new User(0, email, hashedPassword)

  def findUserByEmail(email: String)(implicit schema: CommonSchema) = inTransaction {
    val userList = (from(schema.user)(user ⇒ where(user.email === email) select (user) orderBy (user.email)).distinct).toList
    userList match {
      case List(u) ⇒ Some(u)
      case _ ⇒ None
    }
  }

  def findUserById(id: Long)(implicit schema: CommonSchema): Option[User] = inTransaction { schema.user.lookup(id) }

  def userListByEmail(email: String)(implicit schema: CommonSchema) = inTransaction { from(schema.user)(user ⇒ where(user.email === email) select (user) orderBy (user.email)).toList }

  def CreateNewUser(email: String, password: String)(implicit schema: CommonSchema): Option[String] =
    {
      if (User.userListByEmail(email).length > 0) {
        Option("User with this email already exists already")
      } else {
        val user = new User(0, email, password)
        User.insert(user)
        None
      }
    }

  def insert(u: User)(implicit schema: CommonSchema): User = inTransaction {
    val user = schema.user.insert(u)
    UserActivityLog.insertLog(1, 1 + " new user created " + u.email)
    user
  }
}
