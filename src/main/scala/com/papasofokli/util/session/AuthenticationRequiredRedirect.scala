package com.papasofokli.util.session

import net.liftweb.http.TemporaryRedirectResponse
import net.liftweb.http.RedirectResponse
import net.liftweb.http.Req
import net.liftweb.http.provider.HTTPCookie
import net.liftweb.http.LiftResponse
import net.liftweb.common.{ Loggable, Box, Empty, Full }
import net.liftweb.http.S
import net.liftweb.sitemap.Loc.{ EarlyResponse, LocParam }

/**
 * If a user tries to access a resource that requires authentication,
 * should be redirected to login page, and then back to his original request
 */
object AuthenticationRequiredRedirect extends Loggable {

  //val unauthenticatedEarlyResponse: LocParam = EarlyResponse(checkAndRedirect)

  def checkAndRedirect(): Box[LiftResponse] = {
    logger.info(SessionVariables.AuthenticatedUser.get)

    if (SessionVariables.AuthenticatedUser.get == None)
      Full(TemporaryRedirectResponse("/static/login", S.request.openOrThrowException("No request found"), S.receivedCookies: _*))
    else
      Empty
  }
}

