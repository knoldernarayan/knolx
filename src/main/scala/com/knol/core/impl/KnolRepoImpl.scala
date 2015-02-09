package com.knol.core.impl
import com.knol.core._
import com.knol.db.connection._
import scala.collection.mutable.MutableList
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import org.slf4j.LoggerFactory
class KnolRepoImpl extends KnolRepo with DBConnection {
  val KNOL_ID = 1
  val KNOL_NAME = 2
  val KNOL_EMAIL = 3
  val KNOL_MOBILE_NO = 4
  /**
   * createKnol is method which takes Knolder's object as argument , persist into database and return auto incremented knolder's ID.
   */
  def createKnol(knol: Knolder): Option[Int] = {
    val connection = getConnection()
    val KNOL_NAME = 1
    val KNOL_EMAIL = 2
    val KNOL_MOBILE_NO = 3
    connection match {
      case Some(con) =>
        try {
          val prepareStatement: PreparedStatement = con.prepareStatement("insert into knol(name,email,mobileno) values(?,?,?)")
          prepareStatement.setString(KNOL_NAME, knol.name)
          prepareStatement.setString(KNOL_EMAIL, knol.email)
          prepareStatement.setString(KNOL_MOBILE_NO, knol.mobileNo)
          prepareStatement.executeUpdate()
          val NewSql = "SELECT LAST_INSERT_ID()"
          val rs: ResultSet = prepareStatement.executeQuery(NewSql)
          rs.next()
          val KNOL_ID = 1
          val CREATE_ID = rs.getInt(KNOL_ID)
          logger.debug("Created id" + CREATE_ID)
          // if(result==0)throw new Exception()
          Some(CREATE_ID)
        } catch {
          case ex: Exception =>
            logger.error("Error in creating knol", ex)
            None
        }
      case None => None
    }
  }
/**
 * update is a method which takes Knolder's object as argument ,update the database and return number of effected row.
 */
  def update(knol: Knolder): Int =
    {
      val connection = getConnection()
      val KNOL_NAME = 1
      val KNOL_EMAIL = 2
      val KNOL_MOBILE_NO = 3
      val KNOL_ID = 4
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("update knol set name=?,email=?,mobileno=? where id=?");
            prepareStatement.setString(KNOL_NAME, knol.name);
            prepareStatement.setString(KNOL_EMAIL, knol.email);
            prepareStatement.setString(KNOL_MOBILE_NO, knol.mobileNo);
            prepareStatement.setInt(KNOL_ID, knol.id);
            val result = prepareStatement.executeUpdate();
            logger.debug("Number of knolRows update are " + result)
            result
          } catch {
            case ex: Exception =>
              logger.error("Error in updating knol", ex)
              0
          }
        case None =>
          0
      }
    }
  /**
   * delete is a method which takes knolder's ID as argument,delete particular raw and returns 1.
   *  which indicate number of deleted raws .
   */
  def delete(id: Int): Int =
    {
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("delete from knol where id=" + id)
            val result = prepareStatement.executeUpdate();
            logger.debug("Number of knolRows deleted are " + result)
            result
          } catch {
            case ex: Exception =>
              logger.error("Error in deleting knol", ex)
              0
          }
        case None =>
          0
      }
    }
  /**
   * getKnol is a method which takes knolder's ID as argument and returns knolder's object from database.
   */
  def getKnol(id: Int):Knolder =
    {
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("select * from knol where id=" + id)
            val rs: ResultSet = prepareStatement.executeQuery()
            rs.next()
            val newKnolder = new Knolder(rs.getInt(KNOL_ID), rs.getString(KNOL_NAME), rs.getString(KNOL_EMAIL), rs.getString(KNOL_MOBILE_NO))
            logger.debug("Returned knol is " + newKnolder)
           newKnolder
          } catch {
            case ex: Exception =>
              logger.error("Error in retrieveing knol", ex)
              Knolder(0, "", "", "")
          }
        case None => Knolder(0, "", "", "")
      }
    }
  /**
   * getList is a method which returns list of knolder's object.
   */
  def getList(): MutableList[Knolder] =
    {
      val knolList = MutableList[Knolder]()
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("select * from knol")
            val rs: ResultSet = prepareStatement.executeQuery()
            while (rs.next()) {
              knolList += (Knolder(rs.getInt(KNOL_ID), rs.getString(KNOL_NAME), rs.getString(KNOL_EMAIL), rs.getString(KNOL_MOBILE_NO)));
            }
            logger.debug("Returned knols are " + knolList)
            knolList
          } catch {
            case ex: Exception =>
              logger.error("Error in retriveing knols", ex)
              knolList
          }
        case None => knolList
      }
    }
}

