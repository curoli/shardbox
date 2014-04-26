package controllers

import play.api._
import play.api.mvc._
import uimodels.ShardBoxAppInfo

object ShardBoxController extends Controller {
  
  def index = Action {
    Ok(views.html.index(ShardBoxAppInfo))
  }
  
  def login = Action { implicit request =>
    Ok("Holdon")
    
  }
  
}