package com.knol.core.impl

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
import java.sql.Statement
import java.sql.ResultSet
import com.knol.core.Knolder
/**
 * This is KnolRepoImplTest class which is use for test all methods define in KnolRepoImpl class.
 */
class KnolRepoImplTest extends FunSuite with BeforeAndAfter with DBConnection {

  val KnolRepoImpl = new KnolRepoImpl
  before {
    val connection = getConnection()
    connection match {
      case Some(con) =>
        try {
          val statement = con.createStatement()
          val query = "create table knol(id int(4)primary key auto_increment,name varchar(30),email varchar(30) unique key,mobileno varchar(15));"
          val result = statement.executeUpdate(query)
          val knol = Knolder(1, "narayan ", "cv2vv ", "vvmvsv")
          KnolRepoImpl.createKnol(knol)
          con.close()
          statement.close()

        } catch {
          case ex: Exception =>
            ex.printStackTrace()
            None
        }
      case None => None
    }
  }
  after {
    val connection = getConnection()
    connection match {
      case Some(con) =>
        try {
          val statement = con.createStatement()
          val query = "drop table knol;"
          statement.execute(query)
          con.close()
          statement.close()
        } catch {
          case ex: Exception =>
            ex.printStackTrace()
            None
        }
      case None => None
    }
  }
  test("Create a knol") {
    val knol = Knolder(1, "narayan ", "narayan@knoldus.com", "vvmvsv")
    assert(KnolRepoImpl.createKnol(knol) === Some(2))
  }
  test("Update a knol") {
    val knol = Knolder(1, "narayan", "narayan@knoldus.com", "12344567")
    assert(KnolRepoImpl.update(knol) === 1)
  }
  test("Delete a knol") {
    assert(KnolRepoImpl.delete(1) === 1)
  }
  test("getKnol a knol") {
    val result = KnolRepoImpl.getKnol(1)
    assert(result.id === 1)
  }
  test("getList of a knol list") {
    val result = KnolRepoImpl.getList()
    assert(result.iterator.size === 1)
  }
}