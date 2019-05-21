package models

import play.api.libs.json._


case class Order(order_id: Long, user_id:Long, status: String,
                 price_total: Float)

object Order {
  implicit val orderFormat = Json.format[Order]
}
