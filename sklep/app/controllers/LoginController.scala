package controllers

import javax.inject.Inject
import play.api.mvc._

class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def loginView = Action {
    Ok(views.html.index("LOGIN view"))
  }

  def loginAction = {

  }
}
