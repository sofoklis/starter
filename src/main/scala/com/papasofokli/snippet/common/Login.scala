package com.papasofokli.snippet.common
import net.liftweb.util.Helpers._
import net.liftweb.http._
import S._
import js._
import JsCmds._
import JE._
import net.liftweb.util._
import Helpers._
import net.liftweb.common.Loggable

class Login extends Loggable {

  var email = ""
  var password = ""

  def updateEmail(value: String): JsCmd = {
    email = value
    Noop
  }

  def updatePassword(value: String): JsCmd = {
    password = value
    Noop
  }

  def aCall(id: String, fun: String ⇒ JsCmd): GUIDJsExp =
    SHtml.ajaxCall(JsRaw("$('#" + id + "').val()"), fun)

  val emailCall = aCall("email", updateEmail).exp.toJsCmd
  val passwordCall = aCall("password", updatePassword).exp.toJsCmd

  def login(dummy: String): JsCmd = {
    println("login")
    logger.info(s"Athentigating email:$email password:$password")
    Noop
  }

  lazy val buttonCall: (String, net.liftweb.http.js.JsExp) =
    SHtml.ajaxCall(JsRaw(""), login)

  def render: CssSel =
    "#script" #> script &
      "#email [value]" #> email &
      "#password [value]" #> password &
      "#login [onClick]" #> buttonCall &
      "#loginscript" #> script

  def script =
    Script(JsRaw("""
    	$(function() {
    		$('#email').keyup(function()
    		{
    			""" + emailCall + """
    		});
    	})
    	$(function() {
    		$('#password').keyup(function()
    		{
    			""" + passwordCall + """
    		});
    	})
    """))
}