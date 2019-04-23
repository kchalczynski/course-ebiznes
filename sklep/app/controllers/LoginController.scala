package controllers

import play.api.mvc._

class LoginController {
  def loginView = Action {
    Ok(views.html.index("LOGIN view"))
  }

  def loginAction = {

  }
}
