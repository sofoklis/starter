package com.papasofokli.snippet.component

import net.liftweb.util.Helpers._
import net.liftweb.http._
import S._
import js._
import JsCmds._
import JE._
import net.liftweb.util._
import Helpers._
import net.liftweb.common.Loggable
import com.papasofokli.util.security.Authentication
import com.papasofokli.model.common.User
import com.papasofokli.model.full.ImplicitVal._
import com.papasofokli.util.session.SessionVariables
import com.papasofokli.snippet.component._
import net.liftweb.http.LiftSession._
import net.liftweb.common._
/**
 * Do nothing for now
 */
class NavBar extends Loggable {

  def logout(dummy: String) = {
    // Will kill the session and redirect to login page if session is there, do nothing otherwise
    logger.info(s"Logout ${SessionVariables.AuthenticatedUser.getOrElse("-----")}")
    S.session.foreach(_.destroySession())
    S.redirectTo(loginPage)
    Noop
  }

  val logoutACall = SHtml.ajaxCall(JsRaw(""), logout)
  val loginPage = "/static/login"
  //def render = PassThru

  def render: CssSel = {
    S.appendJs(jsRaw)
    "#logoutlink" #> PassThru
  }

  val jsRaw = JsRaw("""
    	$(function() {
    		$('#logoutlink').click(function(){
    			""" + logoutACall.exp.toJsCmd + """;
    		});
    	})
    """)

}