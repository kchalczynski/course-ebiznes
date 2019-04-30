package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class RegistrationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def registrationView = Action {
    Ok(views.html.index("This is registration view"))
  }

  def registrationAction = Action {
    Ok(views.html.index("This is registration action"))
  }
}
