package controllers

import javax.inject.Inject
import play.api.mvc._

class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def loginView = Action {
    Ok(views.html.index("This is login view"))
  }

  def loginAction = Action {
    Ok(views.html.index("This is login action"))
  }
}
