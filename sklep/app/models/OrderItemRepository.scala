package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import models.OrderRepository
import models.ProductRepository
import scala.concurrent.{ExecutionContext, Future}

class OrderItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, orderRepository: OrderRepository, productRepository: ProductRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "order_item") {

    def order_item_id = column[Long]("pd_id", O.PrimaryKey, O.AutoInc)

    def order_id = column[Long]("order_id")

    def product_id = column[Long]("product_id")

    def quantity = column[Int]("quantity")

    def price_total = column[Float]("total_price")

    def order_fk = foreignKey("order_fkey", order_id, order)(_.order_id)
    def product_fk = foreignKey("product_fkey", product_id, product)(_.product_id)

    def * = (order_item_id, order_id, product_id, quantity,price_total ) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
  }


  import orderRepository.OrderTable
  import productRepository.ProductTable

  private val order_item = TableQuery[OrderItemTable]
  private val order = TableQuery[OrderTable]
  private val product = TableQuery[ProductTable]

  def create(order_id: Long, product_id: Long, quantity: Int, price_total: Float): Future[OrderItem] = db.run {
    (order_item.map(oi => (oi.order_id, oi.product_id, oi.quantity, oi.price_total))
      returning order_item.map(_.order_item_id)
      into { case ((order_id, product_id, quantity, price_total), order_item_id) => OrderItem(order_item_id, order_id, product_id, quantity, price_total) }
      ) += (order_id, product_id, quantity, price_total)
  }

  def list(): Future[Seq[OrderItem]] = db.run {
    order_item.result
  }
}