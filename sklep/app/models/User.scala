package models

import java.sql.Timestamp

import play.api.libs.json._

case class User (user_id: Long, email_address: String, display_name:String, password: String,
            first_name: String, last_name: String, address_1: String, address_2: String,
            phone_number: String, verified:Boolean, password_reset: Boolean, created_date: Timestamp,
            modified_date: Timestamp, payment_info: String)

object User {
  /** Overwrite the default Reads and Writes for Timestamp*/
  implicit val tsreads: Reads[Timestamp] = Reads.of[Long] map (new Timestamp(_))
  implicit val tswrites: Writes[Timestamp] = Writes { (ts: Timestamp) => JsString(ts.toString)}
  implicit val userFormat = Json.format[User]
}
