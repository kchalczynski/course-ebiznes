package models

import play.api.libs.json._

case class Category(category_id: Long, category_name: String)

object Category {
  implicit val orderFormat = Json.format[Category]
}
