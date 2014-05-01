package uimodels

import scala.util.Random
import webutil.WebForms
import play.api.data.Form
import play.api.mvc.Request

object SessionId {

  val random = new Random

  def fresh = SessionId("" + System.currentTimeMillis() + "-" + random.nextLong)

}

case class SessionId(id: String)
case class UserId(id: String)

object ShardBoxSession {

  def fresh = new ShardBoxSession(SessionId.fresh, ShardBoxAppInfo, None, Some(WebForms.login),
    Map.empty[String, String])

}

case class ShardBoxSession(val id: SessionId, val appInfo: AppInfo, val userId: Option[UserId],
  val loginForm: Option[Form[UserCredentials]], val session: Map[String, String]) {

  def withLoginForm(loginForm : Form[UserCredentials]) = copy(loginForm = Some(loginForm))
  
  def withLoginFormBoundFromRequest(implicit request:Request[_]) = {
    val formBound = (loginForm match {
      case Some(loginForm) => loginForm
      case None => WebForms.login
    }).bindFromRequest
    withLoginForm(formBound)
  }
  
}

    