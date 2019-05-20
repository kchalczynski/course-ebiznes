package models

import java.sql.Timestamp

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user") {

    def user_id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

    def email_address = column[String]("email_address")

    def display_name = column[String]("display_name")
    def password = column[String]("password")
    def first_name = column[String]("first_name")
    def last_name = column[String]("last_name")
    def address_1 = column[String]("address_1")
    def address_2 = column[String]("address_2")
    def phone_number = column[String]("phone_number")
    def verified = column[Boolean]("verified")
    def password_reset = column[Boolean]("password_reset")
    def created_date = column[Timestamp]("created_date")
    def modified_date = column[Timestamp]("modified_date")
    def payment_info = column[String]("payment_info")


    def * = (user_id, email_address, display_name, password, first_name,
      last_name, address_1, address_2, phone_number, verified, password_reset,
      created_date, modified_date, payment_info) <> ((User.apply _).tupled, User.unapply)
  }

  private val user = TableQuery[UserTable]

  def create(email_address: String, display_name:String, password: String,
             first_name: String, last_name: String, address_1: String, address_2: String,
             phone_number: String, verified:Boolean, password_reset: Boolean, created_date: Timestamp,
             modified_date: Timestamp, payment_info: String): Future[User] = db.run {

    (user.map(u => (u.email_address, u.display_name, u.password, u.first_name,
      u.last_name, u.address_1, u.address_2, u.phone_number, u.verified, u.password_reset,
      u.created_date, u.modified_date, u.payment_info))
      returning user.map(_.user_id)
      into { case ((email_address, display_name, password, first_name,
        last_name, address_1, address_2, phone_number, verified, password_reset,
        created_date, modified_date, payment_info), user_id) => User(user_id, email_address, display_name, password, first_name,
      last_name, address_1, address_2, phone_number, verified, password_reset,
      created_date, modified_date, payment_info) }
      ) += (email_address, display_name, password, first_name,
      last_name, address_1, address_2, phone_number, verified, password_reset,
      created_date, modified_date, payment_info)
  }

  def list(): Future[Seq[User]] = db.run {
    user.result
  }

}
