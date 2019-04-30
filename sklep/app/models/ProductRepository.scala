package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.CategoryRepository
import scala.concurrent.{ExecutionContext, Future}


/**
  * A repository for products.
  *
  * @param dbConfigProvider The Play db config provider. Play will inject this for you.
  */
@Singleton
class ProductRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, categoryRepository: CategoryRepository)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  /**
    * Here we define the table. It will have a name of
    */
  class ProductTable(tag: Tag) extends Table[Product](tag, "product") {

    /** The ID column, which is the primary key, and auto incremented */
    def product_id = column[Long]("product_id", O.PrimaryKey, O.AutoInc)

    /** The name column */
    def name = column[String]("name")


    def category_id = column[Long]("category_id")

    def price = column[Float]("price")

    def short_desc = column[String]("short_description")

    def details = column[String]("details")

    def available = column[Boolean]("available")

    def available_quantity = column[Int]("available_quantity")

    def category_fk = foreignKey("product_fkey", category_id, category)(_.category_id)


    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the Person object.
      *
      * In this case, we are simply passing the id, name and page parameters to the Person case classes
      * apply and unapply methods.
      */
    def * = (product_id, name, category_id, price, short_desc, details,
      available, available_quantity) <> ((Product.apply _).tupled, Product.unapply)
  }

  import categoryRepository.CategoryTable

  private val product = TableQuery[ProductTable]

  private val category = TableQuery[CategoryTable]

  def create(name: String, category_id: Long, price: Float, short_desc: String, details: String,
             available: Boolean, available_quantity: Int): Future[Product] = db.run {

    (product.map(p => (p.name, p.category_id, p.price, p.short_desc, p.details, p.available, p.available_quantity))

      returning product.map(_.product_id)

      into { case ((name, category_id, price, short_desc, details, available, available_quantity), product_id) =>
        Product(product_id, name, category_id, price, short_desc, details, available, available_quantity)
      };

    ) += (name, category_id, price, short_desc, details, available, available_quantity)
  }


  def list (): Future[Seq[Product]] = db.run {
    product.result
  }
}
