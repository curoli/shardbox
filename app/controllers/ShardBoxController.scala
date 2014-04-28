package controllers

import play.api._
import play.api.mvc._
import uimodels.ShardBoxAppInfo
import webutil.WebForms

object ShardBoxController extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(ShardBoxAppInfo, WebForms.login))
  }

  def login = Action { implicit request =>
    WebForms.login.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.index(ShardBoxAppInfo, formWithErrors))
      },
      credentials => {
        Ok("Holdon!")
      })
  }

}