package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductDetailsRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, val productRepository: ProductRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
//
//  import productRepository.ProductRepository

  class ProductDetailsTable(tag: Tag) extends Table[ProductDetails](tag, "product_details") {

    def pd_id = column[Long]("pd_id", O.PrimaryKey, O.AutoInc)

    def product_id = column[Long]("product_id")

    def full_desc = column[String]("full_description")

    def category_fk = foreignKey("product_details_fkey", product_id, product)(_.product_id)

    def * = (pd_id, product_id, full_desc) <> ((ProductDetails.apply _).tupled, ProductDetails.unapply)
  }


  import productRepository.ProductTable

  val product_details = TableQuery[ProductDetailsTable]
  val product = TableQuery[ProductTable]

  def create(product_id: Long, full_desc: String): Future[ProductDetails] = db.run {
    (product_details.map(pd => (pd.product_id, pd.full_desc))
      returning product_details.map(_.pd_id)
      into { case ((product_id, full_desc), pd_id) => ProductDetails(pd_id, product_id, full_desc) }
      ) += (product_id, full_desc)
  }

  def list(): Future[Seq[ProductDetails]] = db.run {
    product_details.result
  }

}
