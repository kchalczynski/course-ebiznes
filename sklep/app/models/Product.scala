package models

import play.api.libs.json._


case class Product(product_id: Long, name: String, category_id:Long, price: Float,
                   short_desc: String, details: String, available: Boolean,
                   available_quantity: Int)

object Product {
  implicit val productFormat = Json.format[Product]
}
