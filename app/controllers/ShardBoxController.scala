package controllers

import play.api._
import play.api.mvc._
import uimodels.ShardBoxAppInfo
import webutil.WebForms
import uimodels.ShardBoxSession

object ShardBoxController extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(ShardBoxSession.fromRequest(request)))
  }

  def login = Action { implicit request =>
    val session = ShardBoxSession.fromRequest(request)
    session.loginForm.fold(
      formWithErrors => {
        Ok(views.html.index(session))
      },
      credentials => {
        Ok(views.html.index(session))
      })
  }

}