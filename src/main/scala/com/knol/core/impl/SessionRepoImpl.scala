package com.knol.core.impl

import com.knol.core.SessionRepo
import com.knol.core._
import com.knol.db.connection.DBConnection
import scala.collection.mutable.MutableList
import java.sql.PreparedStatement
import java.sql.ResultSet
class SessionRepoImpl extends SessionRepo with DBConnection {

  val KNOLX_ID = 1
  val KNOLX_TOPIC = 2
  val KNOLX_DATE = 3
  val KNOL_ID = 4
  /**
   * createSession is method which takes KnolSession object as argument , persist into database and returns auto incremented knolSession ID.
   */
  def createSession(knolx: KnolSession): Option[Int] =
    {
      val connection = getConnection()
      val KNOLX_TOPIC = 1
      val KNOLX_DATE = 2
      val KNOL_ID = 3
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("insert into knolsession(topic,session_date,knolid) values(?,?,?)")
            prepareStatement.setString(KNOLX_TOPIC, knolx.topic)
            prepareStatement.setString(KNOLX_DATE, knolx.date)
            prepareStatement.setInt(KNOL_ID, knolx.knolId)
            prepareStatement.executeUpdate()
            val NewSql = "SELECT LAST_INSERT_ID()"
            val rs: ResultSet = prepareStatement.executeQuery(NewSql)
            rs.next()
            val KNOLX_ID = 1
            val CREATE_ID = rs.getInt(KNOLX_ID)
            logger.debug("Created id " + CREATE_ID)
            Some(CREATE_ID)
          } catch {
            case ex: Exception =>
              logger.error("Error in creating knolx", ex)
              None
          }
        case None => None
      }

    }
  /**
   * update is a method which takes KnolSession object as argument ,update the database and returns number of effected row.
   */
  def update(knolx: KnolSession): Int =
    {

      val connection = getConnection()
      val KNOLX_TOPIC = 1
      val KNOLX_DATE = 2
      val KNOL_ID = 3
      val KNOLX_ID = 4
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("update knolsession set topic=?,session_date=?,knolid=? where id=?");
            prepareStatement.setString(KNOLX_TOPIC, knolx.topic);
            prepareStatement.setString(KNOLX_DATE, knolx.date);
            prepareStatement.setInt(KNOL_ID, knolx.knolId);
            prepareStatement.setInt(KNOLX_ID, knolx.knolSessionId);
            val result = prepareStatement.executeUpdate();
            logger.debug("Number of knolxRows update are " + result)
            result
          } catch {
            case ex: Exception =>
              logger.error("Error in updating knolx", ex)
              0
          }
        case None =>
          0
      }

    }
  /**
   * delete is a method which takes knolSession ID as argument,delete particular raw and returns 1.
   *  which indicate number of deleted raws .
   */
  def delete(id: Int): Int =
    {
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("delete from knolsession where id=" + id)
            val result = prepareStatement.executeUpdate();
            logger.debug("Number of knolxRows deleted are " + result)
            result
          } catch {
            case ex: Exception =>
              logger.error("Error in deleting knolx", ex)
              0
          }
        case None =>
          0
      }
    }
  /**
   * getKnolSession is a method which takes knolSession ID as argument and returns knolderSession object from database.
   */
  def getKnolSession(id: Int): KnolSession =
    {
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("select * from knolsession where id=" + id)
            val rs: ResultSet = prepareStatement.executeQuery()
            rs.next()
            val newKnolx = new KnolSession(rs.getInt(KNOLX_ID), rs.getString(KNOLX_TOPIC), rs.getString(KNOLX_DATE), rs.getInt(KNOL_ID))
            logger.debug("Returned knolx is " + newKnolx)
            newKnolx
          } catch {
            case ex: Exception =>
              logger.error("Error in retriveing knol ", ex)
              KnolSession(0, "", "", 0)
          }
        case None => KnolSession(0, "", "", 0)
      }
    }
  /**
   * getList is a method which returns list of knolSession object.
   */
  def getList(): MutableList[KnolSession] =
    {
      val knolxList = MutableList[KnolSession]()
      val connection = getConnection()
      connection match {
        case Some(con) =>
          try {
            val prepareStatement: PreparedStatement = con.prepareStatement("select * from knolsession")
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

}
