package bootstrap.liftweb

import net.liftweb.http.{ Html5Properties, LiftRules, Req, S }
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
import com.papasofokli.util.session.AuthenticationRequiredRedirect
import net.liftweb.http.RedirectResponse
import net.liftweb.http.TemporaryRedirectResponse
import com.papasofokli.snippet.full._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {

  def checkLoggedIn(): Boolean = {
    SessionVariables.RedirectAfterLogin(Some(S.request.get.path.partPath.mkString("/", "/", "")))
    SessionVariables.AuthenticatedUser.get != None
  }

  def loggedIn = If(checkLoggedIn, () =>
    //TemporaryRedirectResponse(S.request.get.uri, S.request.get, S.request.get.cookies: _*))
    RedirectResponse(loginPage))

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.papasofokli")
    //LiftRules.httpAuthProtectedResource +=

    //Logger.setup = Full(Log4j.withDefault()); 
    FullDatabaseSchema.initH2Session

    // Build SiteMap
    def sitemap(): SiteMap = SiteMap(
      Menu.i("Home") / "common" / "index" >> loggedIn,
      Menu.i("Seaman") / "crewing" / ** >> loggedIn, // everything under crewing needs to have a user logged in
      Menu.i("Login") / "common" / "login")

    LiftRules.setSiteMap(sitemap)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    if (true) {
      FullDatabaseSchema.recreateDb
      logger.info("Database recreated")
    }
  }
}
