package bootstrap.liftweb

import net.liftweb.http.{ Html5Properties, LiftRules, Req }
import net.liftweb.sitemap.{ Menu, SiteMap }
import net.liftweb.sitemap.Loc
import com.papasofokli.model.common.ImportSampleData
import com.papasofokli.model.common.CommonSchema
import com.papasofokli.model.full.FullDatabaseSchema
import net.liftweb.common.Loggable

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.papasofokli")

    //Logger.setup = Full(Log4j.withDefault()); 
    FullDatabaseSchema.initH2Session

    // Build SiteMap
    def sitemap(): SiteMap = SiteMap( //Menu.i("Home") / "static" / "index",
    //Menu.i("Login") / "templates-hidden" / "login"
    )

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    if (true) {
      FullDatabaseSchema.recreateDb
      logger.info("Database recreated")
    }
  }
}
