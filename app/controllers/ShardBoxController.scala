package controllers

import play.api._
import play.api.mvc._
import uimodels.ShardBoxAppInfo
import webutil.WebForms
import uimodels.ShardBoxSession

object ShardBoxController extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(ShardBoxSession.fresh))
  }

  def login = Action { implicit request =>
    val session = ShardBoxSession.fresh.withLoginFormBoundFromRequest
    session.loginForm.get.fold(
      formWithErrors => {
        Ok(views.html.index(session.withLoginForm(formWithErrors)))
      },
      credentials => {
        Ok("Holdon!")
      })
  }

}