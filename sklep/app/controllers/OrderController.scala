package controllers
import models._
import javax.inject.Inject
import models.{OrderRepository, UserRepository}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.data.format.Formats._


import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class OrderController @Inject()(orderRepo: OrderRepository, userRepo: UserRepository,
                                  cc: MessagesControllerComponents)
                                 (implicit ec: ExecutionContext)extends MessagesAbstractController(cc) {

  val orderForm: Form[CreateOrderForm] = Form {
    mapping(
      "user_id" -> longNumber,
      "status" -> nonEmptyText,
      "price_total" -> of(floatFormat)
    )(CreateOrderForm.apply)(CreateOrderForm.unapply)
  }

  def index = Action.async { implicit request =>
    val orders = orderRepo.list()
    orders.map(order => Ok(views.html.index(orderForm, order)))
  }

  def getAllOrders = Action.async { implicit request =>
    orderRepo.list().map { orders =>
      Ok(Json.toJson(orders))
    }
  }

  def getOrderById(id: Long) = Action.async { implicit request =>
    orderRepo.getById(id).map { orders =>
      Ok(Json.toJson(orders))
    }
  }

  def getOrdersByUserId(user_id: Long) = Action.async { implicit request =>
    orderRepo.getByUserId(user_id).map { orders =>
      Ok(Json.toJson(orders))
    }
  }

  def addOrder = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    var a:Seq[User] = Seq[User]()
    val users = userRepo.list().onComplete{
      case Success(user) => a= user
      case Failure(_) => print("fail")
    }

    orderForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          //          Ok(views.html.index(errorForm,a))
          Ok("Nie udało sie")
        )
      },
      order => {
        orderRepo.create(order.user_id, order.status, order.price_total).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.OrderController.index).flashing("success" -> "order.created")
        }
      }
    )
  }

  def deleteById(id: Long) = Action {
    Ok("Deleting order By id")
  }

  def updateOrder(id: Long) = Action { implicit request =>
    Ok("update order")
  }
}

case class CreateOrderForm(user_id: Long, status: String, price_total: Float)
//class OrderController @Inject()(cc: ControllerComponents) extends AbstractController(cc){
//
//  def getAllOrders = Action {
//    Ok(views.html.index("This is getAllOrders view"))
//  }
//
//  def getOrderById(id: Integer) = Action {
//    Ok(views.html.index("This is getOrderById view"))
//  }
//
//  def getOrdersByUserId(userId:Integer) = Action {
//    Ok(views.html.index("This is getOrdersByUserId view"))
//  }
//
//  def orderProduct = Action {
//    Ok(views.html.index("This is orderProductAction"))
//  }
//}
