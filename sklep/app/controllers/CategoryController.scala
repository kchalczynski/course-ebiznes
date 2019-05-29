package controllers

import javax.inject._
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class CategoryController  @Inject()(categoryRepo: CategoryRepository, cc: MessagesControllerComponents)
    (implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc) {

  /**
    * The mapping for the category form.
    */
  val categoryForm: Form[CreateCategoryForm] = Form {
    mapping(
    "category_name" -> nonEmptyText
    )(CreateCategoryForm.apply)(CreateCategoryForm.unapply)
  }

  /**
    * The index action.
    */
  def index = Action.async { implicit request =>
    val categories = categoryRepo.list()
    categories.map(cat => Ok(views.html.index(categoryForm, cat)))
  }

  /**
    * The add person action.
    *
    * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
    */

  def addCategory = Action.async { implicit request =>
    var a:Seq[Category] = Seq[Category]()
    val categories = categoryRepo.list().onComplete{
    case Success(cat) => a= cat
    case Failure(_) => print("fail")
  }

    categoryForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
        Ok("Nie udało sie")
        )
      },
      category => {
        categoryRepo.create(category.category_name).map { category =>
          Created(Json.toJson(category))
        }
      }
    )
  }

  /**
    * A REST endpoint that gets all the categories as JSON.
    */
  def getAllCategories = Action.async { implicit request =>
    categoryRepo.list().map { categories =>
      Ok(Json.toJson(categories))
    }
  }

  def getCategoryById(id: Long) = Action.async { implicit  request =>
    categoryRepo.getCategoryById(id).map { categories =>
      Ok(Json.toJson(categories))
    }
  }


  def deleteById(id: Long) = Action {
    Ok("Deleting category By id")
  }

  def updateCategory(id: Long) = Action { implicit request =>
    Ok("update Category")
  }

  def handlePost = Action.async { implicit request =>
    val category_name = request.body.asJson.get("category_name").as[String]

    categoryRepo.create(category_name).map { category =>
      Ok(Json.toJson(category))
    }
  }
}

/**
  * The create person form.
  *
  * Generally for forms, you should define separate objects to your models, since forms very often need to present data
  * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
  * that is generated once it's created.
  */
case class CreateCategoryForm(category_name: String)