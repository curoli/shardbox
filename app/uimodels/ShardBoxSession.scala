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

  def fromRequest(request:Request[_]) = {
    ShardBoxSession(SessionId.fresh, request, ShardBoxAppInfo, None, Some(WebForms.login))    
  }

}

case class ShardBoxSession(val id: SessionId, val request:Request[_], val appInfo: AppInfo, 
    val userId: Option[UserId], val loginForm: Option[Form[UserCredentials]]) {

  def withLoginForm(loginForm : Form[UserCredentials]) = copy(loginForm = Some(loginForm))
  
  def withLoginFormBoundFromRequest = {
    val formBound = (loginForm match {
      case Some(loginForm) => loginForm
      case None => WebForms.login
    }).bindFromRequest()(request)
    withLoginForm(formBound)
  }
  
}

    