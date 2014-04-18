package controllers

import play.api._
import play.api.mvc._

object ShardBoxController extends Controller {
  
  def index = Action {
    Ok(views.html.index("ShardBox, the elections and referenda app", 
"Welcome to ShardBox!", 
"ShardBox is a web-app to allow members of a group to vote in elections and referenda."))
  }
  
}