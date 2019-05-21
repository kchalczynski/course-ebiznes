package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAllProducts = Action {
    Ok(views.html.index("This is getAllProducts view"))
  }

  def getProductById(id: Integer) = Action {
    Ok(views.html.index("This is getProductById view"))
  }

  def getProductDetails(id: Integer) = Action {
    Ok(views.html.index("This is getProductDetails view"))
  }

  def getProductsByCategory(category: String) = Action {
    Ok(views.html.index("This is getProductByCategory view"))
  }

  def addProduct = Action {
    Ok(views.html.index("This is addProduct action"))
  }

  def updateProduct(id: Integer) = Action {
    Ok(views.html.index("This is updateProduct action"))
  }
}
