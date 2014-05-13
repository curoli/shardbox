package uimodels

import scala.util.Random
import webutil.WebForms
import play.api.data.Form
import play.api.mvc.Request
import play.api.mvc.SimpleResult
import play.api.templates.Html
import play.api.mvc.Results.Ok

object SessionId {

  val key = "ShardBoxSessionId"

  val random = new Random

  def fresh = SessionId(System.currentTimeMillis(), random.nextLong)

  def parse(string:String) : SessionId = {
    val array = string.split("#").map(_.toLong)
    SessionId(array(0), array(1))
  }
  
  def fromRequestOrElseFresh(request: Request[_]) = {
    try {
      request.session.get(key).map(parse(_)).getOrElse(fresh)
    } catch {
      case e: Exception => fresh
    }
  }

}

case class SessionId(time: Long, random: Long) {
  def asString = time + "#" + random
}

case class UserId(string: String)

object ShardBoxSession {

  def fromRequest(request: Request[_]) = {
    ShardBoxSession(SessionId.fromRequestOrElseFresh(request), request, ShardBoxAppInfo, None)
  }

}

case class ShardBoxSession(val id: SessionId, val request: Request[_], val appInfo: AppInfo,
  val userId: Option[UserId]) {

  lazy val loginForm = WebForms.login.bindFromRequest()(request)

  def respond(responder: ShardBoxSession => Html): SimpleResult = {
    Ok(responder(this)).withSession(request.session + (SessionId.key -> id.asString))
  }

}

    