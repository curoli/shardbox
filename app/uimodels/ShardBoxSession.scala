package uimodels

import scala.util.Random
import webutil.WebForms
import play.api.data.Form
import play.api.mvc.Request
import play.api.mvc.SimpleResult
import play.api.templates.Html
import play.api.mvc.Results.Ok
import store.SessionStore

case class UserId(string: String)

object SessionId {

  val key = "ShardBoxSessionId"

  val random = new Random

  def fresh = SessionId(System.currentTimeMillis(), random.nextLong)

  def parse(string: String): SessionId = {
    val array = string.split("#").map(_.toLong)
    SessionId(array(0), array(1))
  }

  def fromRequest(request: Request[_]) :Option[SessionId] = {
    try {
      request.session.get(key).map(parse(_))
    } catch {
      case e: Exception => None
    }
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

object ShardBoxSession {

  def fromRequest(request: Request[_]) = {
    
    val sessionId = SessionId.fromRequestOrElseFresh(request)
    val loginForm = WebForms.login.bindFromRequest()(request)
    val credsOpt = loginForm.fold(loginFormWithErrors => None, creds => Some(creds))
    val authenticated = credsOpt match {
      case Some(creds) => UserAuthenticator.authenticate(creds)
      case None => false
    }
    val userIdOpt = credsOpt.map(_.userId).map(UserId(_))
    ShardBoxSession(sessionId, request, ShardBoxAppInfo, authenticated, loginForm, userIdOpt)
  }

}

case class ShardBoxSession(val id: SessionId, val request: Request[_], val appInfo: AppInfo,
    val authenticated:Boolean, val loginForm: Form[UserCredentials], 
    val userIdOpt: Option[UserId]) {

  def respond(responder: ShardBoxSession => Html): SimpleResult = {
    Ok(responder(this)).withSession(request.session + (SessionId.key -> id.asString))
  }

}

    