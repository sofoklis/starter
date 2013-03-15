package com.papasofokli.util.session

import net.liftweb.http.SessionVar
import com.papasofokli.model.common.User

object SessionVariables {
  object AuthenticatedUser extends SessionVar[Option[User]](None)
  object AppliedUser extends SessionVar[Option[User]](AuthenticatedUser.get)
  object RedirectAfterLogin extends SessionVar[Option[String]](None)
}