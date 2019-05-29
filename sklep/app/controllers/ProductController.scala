package controllers
import models._
import javax.inject.Inject
import models.{CategoryRepository, ProductRepository}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.data.format.Formats._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ProductController @Inject()(productRepo: ProductRepository, categoryRepo: CategoryRepository,
                                  cc: MessagesControllerComponents)
                                 (implicit ec: ExecutionContext)extends MessagesAbstractController(cc) {

  val productForm: Form[CreateProductForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "category_id" -> longNumber,
      "price" -> of(floatFormat),
      "short_desc" -> text,
      "details" -> nonEmptyText,
      "available" -> boolean,
      "available_quantity" -> number
    )(CreateProductForm.apply)(CreateProductForm.unapply)
  }

  def index = Action.async { implicit request =>
    val products = productRepo.list()
    products.map(prod => Ok(views.html.index(productForm, prod)))
  }

  def getAllProducts = Action.async { implicit request =>
    productRepo.list().map { products =>
      Ok(Json.toJson(products))
    }
  }

  def getProductById(id: Long) = Action.async { implicit request =>
    productRepo.getById(id).map { products =>
      Ok(Json.toJson(products))
    }
  }
  // jak rozumiec product details
  def getProductDetails(id: Long) = Action.async { implicit request =>
    productRepo.getById(id).map { products =>
      Ok(Json.toJson(products))
    }
  }

  def getProductsByCategory(category: Long) = Action.async { implicit request =>
    productRepo.getByCategory(category).map { products =>
      Ok(Json.toJson(products))
    }
  }

  def addProduct = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    var a:Seq[Category] = Seq[Category]()
    val categories = categoryRepo.list().onComplete{
      case Success(cat) => a= cat
      case Failure(_) => print("fail")
    }

    productForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
//          Ok(views.html.index(errorForm,a))
          Ok("Nie udało sie")
        )
      },
      product => {
        productRepo.create(product.name, product.category_id, product.price, product.short_desc,
          product.details, product.available, product.available_quantity).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.ProductController.index).flashing("success" -> "product.created")
        }
      }
    )
  }

  def deleteById(id: Long) = Action {
    Ok("Deleting product By id")
  }

  def updateProduct(id: Long) = Action { implicit request =>
    Ok("update product")
  }
}

case class CreateProductForm(name: String, category_id: Long, price: Float, short_desc: String,
                             details: String, available: Boolean, available_quantity: Int)