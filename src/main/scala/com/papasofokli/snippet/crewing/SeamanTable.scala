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
import com.papasofokli.util.common.PCache

class SeamanTable {

  def render = {
    S.appendJs(SeamanTable.jsRaw)
    PassThru
  }
}

object SeamanTable extends Loggable {
  val q = "\""

  def p2A(p: Person): String = s"[$q${p.firstName} ${p.lastName}$q, $q${p.dateOfBirth}$q]"

  def data = "[" +
    (seamanList.foldLeft("")(_ + ", " + p2A(_))).replaceFirst(",", "") +
    "]"

  def jsRaw = JsRaw(""" 
    var asInitVals = new Array();
    var oTable = $('#seamanTable').dataTable({
		  "bStateSave": true,
		  "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		  "sPaginationType": "bootstrap",
		  "oLanguage": {
		  	"sLengthMenu": "_MENU_ records per page"
		  	},
		  "aaData": """ + PCache.getSeamanTable + """
		  ,
		  "aoColumns": [
		  	{ "sTitle": "Name" },
			{ "sTitle": "Date of birth" }
		   ]
	});
	$("thead input").keyup( function () {
		  /* Filter on the column (the index) of this element */
		  oTable.fnFilter( this.value, $("thead input").index(this) );
	});
	/*
	 * Support functions to provide a little bit of 'user friendlyness' to the textboxes in
	 * the footer
	 */
	 $("thead input").each( function (i) {
		  asInitVals[i] = this.value;
	 } );
	     
	 $("thead input").focus( function () {
		  if ( this.className == "search_init" )
	      {
	      	this.className = "";
	        this.value = "";
	      }
	  });
	     
	  $("thead input").blur( function (i) {
	     if ( this.value == "" )
	     {
		  	this.className = "search_init";
	        this.value = asInitVals[$("thead input").index(this)];
	     }
	  } );			
    """)
  def seamanList = inTransaction { logger.info("getting from db"); Person.getAllSorted.toList }
}