package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("ひじき祭会場"))
  }

  def about = Action {
    Ok(views.html.about("ひじき祭会場"))
  }

}
