package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class CartController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getCart = Action {
    Ok(views.html.index("This is GET action for Cart"))
  }

  def updateCart = Action {
    Ok(views.html.index("This is PUT (update) action for Cart"))
  }
}
