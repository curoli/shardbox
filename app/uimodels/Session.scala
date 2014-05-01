package uimodels

import scala.util.Random
import webutil.WebForms
import play.api.data.Form

object SessionId {
  
  val random = new Random
  
  def fresh = SessionId("" + System.currentTimeMillis() + "-" + random.nextLong)
  
}

case class SessionId(id: String)
case class UserId(id:String)

object Session {
  
  def fresh = new Session(SessionId.fresh, ShardBoxAppInfo, None, Some(WebForms.login), 
      Map.empty[String, String])
  
}

class Session(id: SessionId, appInfo: AppInfo, userId : Option[UserId], 
    form : Option[Form[UserCredentials]], session:Map[String, String]) {
  
}

    