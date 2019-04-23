package models

import play.api.libs.json._


case class OrderItem(order_item_id: Long, order_id: Long, product_id:Long, quantity: Int,
                 price_total: Float)

object OrderItem {
  implicit val productFormat = Json.format[OrderItem]
}
