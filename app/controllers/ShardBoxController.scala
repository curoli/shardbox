package controllers

import play.api._
import play.api.mvc._
import uimodels.ShardBoxAppInfo
import webutil.WebForms
import uimodels.ShardBoxSession
import uimodels.SessionId

object ShardBoxController extends Controller {

  def index = Action { implicit request =>
    ShardBoxSession.fromRequest(request).respond(views.html.index(_))
  }

  def login = Action { implicit request =>
    ShardBoxSession.fromRequest(request).respond(views.html.index(_))
  }

}