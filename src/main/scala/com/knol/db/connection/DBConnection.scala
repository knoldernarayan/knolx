package com.knol.db.connection
import com.typesafe.config.ConfigFactory
import java.sql.DriverManager
import org.slf4j.LoggerFactory
/**
 * This is database connection traits which is use for database connection.
 */
trait DBConnection {
  val config = ConfigFactory.load()
  val url = config.getString("db.url");
  val user = config.getString("db.username");
  val password = config.getString("db.password");
  val driver = config.getString("db.driver")
  val logger = LoggerFactory.getLogger(this.getClass)
  /**
   * This is one of the method of DBConnection trait which return connection object.
   */
  def getConnection(): Option[java.sql.Connection] =
    {
      try {
        Class.forName(driver)
        val connection = DriverManager.getConnection(url, user, password)
        logger.debug("Connection created")
        Some(connection)
      } catch {
        case e: Exception =>
          e.printStackTrace();
          logger.error("Error in creating connection", e)
          None
      }
    }
}
