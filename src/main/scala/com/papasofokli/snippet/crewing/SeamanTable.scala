package com.papasofokli.snippet.crewing
import com.papasofokli.model._
import shipmanagement._
import common._
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
import com.papasofokli.model.full.ImplicitVal._
import org.squeryl.PrimitiveTypeMode._

class SeamanTable { //(val seamanList: List[Seaman]) {

  def render = {
    S.appendJs(SeamanTable.jsRaw)
    PassThru //"#ttdkdkdkdkdk" #> ""
  }
}

object SeamanTable extends Loggable {
  val q = "\""
  def p2A(p: Person): String = s"[$q${p.firstName} ${p.lastName}$q, $q${p.dateOfBirth}$q]"

  lazy val data = "[" +
    (seamanList.foldLeft("")(_ + ", " + p2A(_))).replaceFirst(",", "") +
    "]"

  def jsRaw = JsRaw(""" 
      $('#seamanTable').dataTable({
			"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
			"sPaginationType": "bootstrap",
			"oLanguage": {
				"sLengthMenu": "_MENU_ records per page"
		  	},
			"aaData": """ + data + """
		    ,
		    "aoColumns": [
				{ "sTitle": "Name" },
				{ "sTitle": "Date of birth" }
		    ]
			
		} );
    """)
  lazy val seamanList = inTransaction { logger.info("getting from db"); Person.getAllSorted.toList }
}