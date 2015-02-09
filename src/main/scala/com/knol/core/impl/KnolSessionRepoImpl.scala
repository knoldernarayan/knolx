package com.knol.core.impl
import com.knol.core._
import scala.collection.mutable.MutableList
import com.knol.db.connection.DBConnection
import java.sql.PreparedStatement
import java.sql.ResultSet
/**
 * This is KnolSessionRepoImpl class which defines all methods of KnolSessionRepo trait.
 */
class KnolSessionRepoImpl extends KnolSessionRepo with DBConnection {
  /**
   * This is getKnolxDetailByKnolId method which takes knol's id as argument and
   * returns list of KnolSession represented by particular knol's.
   */
  def getKnolxDetailByKnolId(id: Int): MutableList[KnolSession] =
    {
      val KNOLX_ID = 1
      val KNOLX_TOPIC = 2
      val KNOLX_DATE = 3
      val KNOL_ID = 4
      val knolxList = MutableList[KnolSession]()
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val query = "select kx.id,kx.topic,kx.session_date,kx.knolid from knolsession kx where kx.knolid=" + id
            val prepareStatement: PreparedStatement = con.prepareStatement(query)
            val rs: ResultSet = prepareStatement.executeQuery()
            while (rs.next()) {
              knolxList += (KnolSession(rs.getInt(KNOLX_ID), rs.getString(KNOLX_TOPIC), rs.getString(KNOLX_DATE), rs.getInt(KNOL_ID)));
            }
            logger.debug("Returned list of knolx are " + knolxList)
            knolxList
          } catch {
            case ex: Exception =>
              logger.error("Error in retrieveing list of knolx", ex)
              knolxList
          }
        case None => knolxList
      }
    }
  /**
   * This is  getKnolDetailByKnolxId method which takes knolx's id as argument and returns Knolder's object.
   */
  def getKnolDetailByKnolxId(id: Int): Knolder =
    {
      val KNOL_ID = 1
      val KNOL_NAME = 2
      val KNOL_EMAIL = 3
      val KNOL_MOBILE_NO = 4
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val query = "select kl.id,kl.name,kl.email,kl.mobileno from knol kl where kl.id=(select knolid from knolsession where id=" + id + ");"
            val prepareStatement: PreparedStatement = con.prepareStatement(query)
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
   * This is getAllKnolSessionDetail method which does not take any argument and returns all combine Knol and KnolSession raws under join operation.
   */
  def getAllKnolSessionDetail(): MutableList[DetailKnolSession] =
    {
      val KNOL_ID = 1
      val KNOL_NAME = 2
      val KNOL_EMAIL = 3
      val KNOL_MOBILE_NO = 4
      val KNOLX_ID = 5
      val KNOLX_TOPIC = 6
      val KNOLX_DATE = 7
      val knolxKnolList = MutableList[DetailKnolSession]()
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val query = "select kl.id,kl.name,kl.email,kl.mobileno, kx.id,kx.topic,kx.session_date from knol kl,knolsession kx where kx.knolid = kl.id"
            val prepareStatement: PreparedStatement = con.prepareStatement(query)
            val rs: ResultSet = prepareStatement.executeQuery()
            while (rs.next()) {
              knolxKnolList += (DetailKnolSession(rs.getInt(KNOL_ID), rs.getString(KNOL_NAME), rs.getString(KNOL_EMAIL), rs.getString(KNOL_MOBILE_NO),
                rs.getInt(KNOLX_ID), rs.getString(KNOLX_TOPIC), rs.getString(KNOLX_DATE)));
            }
            logger.debug("Returned list of knolxKnolList are " + knolxKnolList)
            knolxKnolList
          } catch {
            case ex: Exception =>
              logger.error("Error in retrieveing list of knolxKnolLst", ex)
              knolxKnolList
          }
        case None => knolxKnolList
      }
    }
}
