package com.papasofokli.snippet.component
import net.liftweb.http._
import S._
import js._
import JsCmds._
import JE._
import net.liftweb.util._
import Helpers._
import scala.xml.Text
import com.papasofokli.util.component.ValidationResult._
import com.papasofokli.util.component._

class Input extends SingelValueControlT {

  def calculateValueToSendToServerJsCmd = JsRaw("$('#" + fieldName + "Input" + id + "').val()")

  def aCall: (String, net.liftweb.http.js.JsExp) =
    SHtml.ajaxCall(calculateValueToSendToServerJsCmd, callback(fieldName))

  def clientCall = aCall._2.toJsCmd

  def render: CssSel = {
    S.appendJs(script)
    "#fieldNameInput [value]" #> value &
      "#fieldNameInput [id]" #> (fieldName + "Input" + id)
  }

  def script =
    JsRaw("""
    	$(function() {
    		$('#""" + fieldName + "Input" + id + """').keyup(function()
    		{
    			""" + clientCall + """
    		});
    	})
    """)
}