package controllers

import play.api._
import play.api.mvc._
import uimodels.ShardBoxAppInfo
import webutil.WebForms
import uimodels.ShardBoxSession
import uimodels.SessionId

object ShardBoxController extends Controller {

  def index = Action { implicit request =>
    val sBSession = ShardBoxSession.fromRequest(request)
    Ok(views.html.index(sBSession)).withSession(session + (SessionId.key -> sBSession.id.string))
  }

  def login = Action { implicit request =>
    val sBSession = ShardBoxSession.fromRequest(request)
    sBSession.loginForm.fold(
      formWithErrors => {
        Ok(views.html.index(sBSession))
      },
      credentials => {
        Ok(views.html.index(sBSession))
      }).withSession(session + (SessionId.key -> sBSession.id.string))
  }

}