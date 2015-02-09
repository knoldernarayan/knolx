package com.knol.core.impl
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
import java.sql.Statement
import java.sql.ResultSet
import com.knol.core.Knolder
import scala.collection.mutable.MutableList
class KnolRepoImplEx extends FunSuite with BeforeAndAfter with DBConnection {

  val KnolRepoImpl = new KnolRepoImpl
  before {
    val connection = getConnection()
    connection match {
      case Some(con) =>
        try {
          val statement = con.createStatement()
          val query = "create table knol(id int(4)primary key auto_increment,name varchar(30),email varchar(30) unique key,mobileno varchar(15));"
          val result = statement.executeUpdate(query)
          val knol = Knolder(1, "narayan ", "cv2vv", "vvmvsv")
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
  test("Create a knol with exception") {
    val knol = Knolder(1, "narayan ", "cv2vv", "vvmvsv")
    assert(KnolRepoImpl.createKnol(knol) === None)
  }
  test("Update a knol with Exception") {
    val knol = Knolder(2, "narayan", "narayan@knoldus.com", "12344567")
    assert(KnolRepoImpl.update(knol) === 0)
  }
  
  test("Delete a knol with Exception") {
    assert(KnolRepoImpl.delete(2) === 0)
  }
  test("getKnol a knol with Exception") {
    val result = KnolRepoImpl.getKnol(2)
    assert(result.id ===0)
  }
  
  test("getList of a knol list with Exception") {
    KnolRepoImpl.delete(1)
    val result = KnolRepoImpl.getList()
    assert(result ===MutableList())
  }
}