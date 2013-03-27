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
import scala.xml.NodeSeq
import com.papasofokli.util.security.Authentication
import com.papasofokli.model.common.User
import com.papasofokli.model.full.ImplicitVal._
import com.papasofokli.util.session.SessionVariables
import com.papasofokli.snippet.component._
import com.papasofokli.snippet.component.AlertType
import com.papasofokli.snippet.full._

class Login extends Loggable {
  val successPage = SessionVariables.RedirectAfterLogin.get match {
    case Some(r) => r
    case None => defaultAfterLoginPage
  }

  var unsuccessfullTries = 0

  var email = ""
  var password = ""

  def aCall(id: String, fun: String ⇒ JsCmd): GUIDJsExp =
    SHtml.ajaxCall(JsRaw("$('#" + id + "').val()"), fun)

  val emailCall = aCall("email", email = _).exp.toJsCmd
  val passwordCall = aCall("password", password = _).exp.toJsCmd

  val loginACall: GUIDJsExp = SHtml.ajaxCall(JsRaw(""), login)

  def login(dummy: String): JsCmd = {
    val user: Option[User] = User.findUserByEmail(email)

    val validCredentials = user match {
      case None => false
      case Some(u) => Authentication.check(password, u.hashedPasswd)
    }

    if (validCredentials) {
      logger.info("athentication for $email successfull")
      SessionVariables.AuthenticatedUser.set(user)
      SessionVariables.RedirectAfterLogin(None)
      S.redirectTo(successPage)
    } else {
      unsuccessfullTries += 1
      logger.info(s"athentication for $email unsuccessfull $unsuccessfullTries times")
      SessionVariables.AuthenticatedUser.set(None)
    }
    SHtml.ajaxInvoke(message.setHtml _)
  }

  val message = SHtml.idMemoize(pi ⇒ {
    val alert = AlertMessage.render("Authentication failed", AlertType.Error).get
    if (unsuccessfullTries > 0) "#errorMessage *" #> alert
    else "#errorMessage *" #> NodeSeq.Empty
  })

  def render: CssSel =
    if (SessionVariables.AuthenticatedUser.get == None) {
      S.appendJs(jsRaw)
      "#email [value]" #> email &
        "#password [value]" #> "" &
        "#errorMessageout" #> message
    } else {
      // No need to render if user is already logged in, simply redirect to successPage
      SessionVariables.RedirectAfterLogin(None)
      S.redirectTo(successPage)
    }

  val jsRaw = JsRaw("""
    	$(function() {
    		$('#login').click(function(){
    			""" + emailCall + """;
    			""" + passwordCall + """;
    			""" + loginACall.exp.toJsCmd + """;
    		});
    	})
    """)
}

