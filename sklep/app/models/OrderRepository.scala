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

    def order_id = column[Long]("pd_id", O.PrimaryKey, O.AutoInc)

    def user_id = column[Long]("user_id")

    def status = column[String]("status")

    def price_total = column[Float]("total_price")

    def user_fk = foreignKey("order_fkey", user_id, user)(_.user_id)


    def * = (order_id, user_id, status, price_total) <> ((Order.apply _).tupled, Order.unapply)
  }


  import userRepository.UserTable

  private val order = TableQuery[OrderTable]
  private val user = TableQuery[UserTable]

  def create(user_id: Long, status: String, price_total: Float): Future[Order] = db.run {
    (order.map(o => (o.user_id, o.status, o.price_total))
      returning order.map(_.order_id)
      into { case ((user_id, status, price_total), order_id) => Order(order_id, user_id, status, price_total) }
      ) += (user_id, status, price_total)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }
}