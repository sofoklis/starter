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

class Select extends SingelValueControlT {

  def calculateValueToSendToServerJsCmd = JsRaw("$('#" + fieldName + "Select" + id + "').val()")

  def aCall: (String, net.liftweb.http.js.JsExp) =
    SHtml.ajaxCall(calculateValueToSendToServerJsCmd, callback(fieldName))

  def clientCall = aCall._2.toJsCmd

  def optionList: List[String] = List("Male", "Female")

  def render: CssSel = {
    S.appendJs(script)
    "#fieldNameSelect [id]" #> (fieldName + "Select" + id) &
      "#fieldNameOptions" #> optionList.map(option ⇒ {
        "#fieldNameOptions *" #> option &
          (if (this.value() == option)
            "#fieldNameOptions [selected]" #> "selected"
          else
            "#fdsdfsd" #> "")
      })
  }

  def script =
    JsRaw("""
    	$(function() {
    		$('#""" + fieldName + "Select" + id + """').change(function()
    		{
    			""" + clientCall + """
    		});
    	})
    """)
}