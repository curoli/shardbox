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
    val session = ShardBoxSession.fromRequest(request).withLoginFormBoundFromRequest
    session.loginForm.get.fold(
      formWithErrors => {
        Ok(views.html.index(session.withLoginForm(formWithErrors)))
      },
      credentials => {
        Ok("Holdon!")
      })
  }

}