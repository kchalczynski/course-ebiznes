package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.UserRepository
import scala.concurrent.{ExecutionContext, Future}

class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, userRepository: UserRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class OrderTable(tag: Tag) extends Table[Order](tag, "order") {

    def pd_id = column[Long]("pd_id", O.PrimaryKey, O.AutoInc)

    def product_id = column[Long]("product_id")

    def full_desc = column[String]("full_description")

    def category_fk = foreignKey("product_details_fkey", product_id, product)(_.product_id)


    def * = (pd_id, product_id, full_desc) <> ((Order.apply _).tupled, ProductDetails.unapply)
  }


  import userRepository.UserTable

  private val order = TableQuery[OrderTable]
  private val user = TableQuery[UserTable]

  def create(product_id: Long, full_desc: String): Future[Order] = db.run {
    (order.map(o => (pd.product_id, pd.full_desc))
      returning order.map(_.pd_id)
      into { case ((product_id, full_desc), pd_id) => Order(pd_id, product_id, full_desc) }
      ) += (product_id, full_desc)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }
}