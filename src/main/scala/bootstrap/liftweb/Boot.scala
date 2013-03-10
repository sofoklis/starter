package bootstrap.liftweb

import net.liftweb.http.{ Html5Properties, LiftRules, Req }
import net.liftweb.sitemap.{ Menu, SiteMap }
import com.papasofokli.model.common.ImportSampleData
import com.papasofokli.model.common.CommonSchema
import com.papasofokli.model.full.FullDatabaseSchema
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import net.liftweb.sitemap.Loc
import net.liftweb.sitemap.Loc.Hidden
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import com.papasofokli.util.session.SessionVariables

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.papasofokli")
    //LiftRules.httpAuthProtectedResource +=

    //Logger.setup = Full(Log4j.withDefault()); 
    FullDatabaseSchema.initH2Session

    // Build SiteMap
    def sitemap(): SiteMap = SiteMap(
      Menu.i("Home") / "static" / "index" >> If(() => false, "You must be logged in"),
      Menu.i("Seaman") / "page" / "seaman" >> If(() => false, "You must be logged in"),
      Menu.i("Login") / "static" / "login")

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    if (false) {
      FullDatabaseSchema.recreateDb
      logger.info("Database recreated")
    }
  }
}
