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
    ShardBoxSession(SessionId.fresh, request, ShardBoxAppInfo, None)    
  }

}

case class ShardBoxSession(val id: SessionId, val request:Request[_], val appInfo: AppInfo, 
    val userId: Option[UserId]) {

  lazy val loginForm = WebForms.login.bindFromRequest()(request)
  
}

    