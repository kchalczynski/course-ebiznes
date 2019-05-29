package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc){


  def getAllUsers = Action {
    Ok(views.html.index("This is getAllUsers view"))
  }

  def getUserById(id: Long) = Action {
    Ok(views.html.index("This is getUserById view"))
  }

  def getUserOrders(id: Long) = Action {
    Ok(views.html.index("This is getUserOrders view"))
  }

  def getUserDetails(id: Long) = Action {
    Ok(views.html.index("This is getUserDetails view"))
  }

  def addUser = Action {
    Ok(views.html.index("This is addUser action"))
  }

  def updateUser(id: Long) = Action {
    Ok(views.html.index("This is updateUser action"))
  }
}
