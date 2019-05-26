package models

import play.api.libs.json._

case class ProductDetails(pd_id: Long, product_id: Long, full_desc: String)

object ProductDetails {
  implicit val productDetailsFormat = Json.format[ProductDetails]
}


