package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class OrderController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def getAllOrders = Action {
    Ok(views.html.index("This is getAllOrders view"))
  }

  def getOrderById(id: Integer) = Action {
    Ok(views.html.index("This is getOrderById view"))
  }

  def getOrdersByUserId(userId:Integer) = Action {
    Ok(views.html.index("This is getOrdersByUserId view"))
  }

  def orderProduct = Action {
    Ok(views.html.index("This is orderProductAction"))
  }
}
