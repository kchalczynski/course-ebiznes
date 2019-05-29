package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, val userRepository: UserRepository)(implicit ec: ExecutionContext) {


  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  import userRepository.UserTable

  class OrderTable(tag: Tag) extends Table[Order](tag, "order") {

    def order_id = column[Long]("pd_id", O.PrimaryKey, O.AutoInc)

    def user_id = column[Long]("user_id")

    def status = column[String]("status")

    def price_total = column[Float]("total_price")

    def user_fk = foreignKey("order_fkey", user_id, user)(_.user_id)


    def * = (order_id, user_id, status, price_total) <> ((Order.apply _).tupled, Order.unapply)
  }



  val order = TableQuery[OrderTable]
  val user = TableQuery[UserTable]

  def create(user_id: Long, status: String, price_total: Float): Future[Order] = db.run {
    (order.map(o => (o.user_id, o.status, o.price_total))
      returning order.map(_.order_id)
      into { case ((user_id, status, price_total), order_id) => Order(order_id, user_id, status, price_total) }
      ) += (user_id, status, price_total)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }

  def getById(id: Long): Future[Seq[Order]] = db.run {
    order.filter(_.order_id === id).result
  }

  def getByUserId(user_id: Long): Future[Seq[Order]] = db.run {
    order.filter(_.user_id == user_id).result
  }
}