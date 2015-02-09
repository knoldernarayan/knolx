package com.knol.core.impl
import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
import com.knol.core.KnolSession
import com.knol.core.Knolder
import org.scalatest.FunSuite
import scala.collection.mutable.MutableList
/**
 * This is SessionRepoImplTest class which is use for test all methods define in SessionRepoImpl class.
 */
class SessionRepoImplEx extends FunSuite with BeforeAndAfter with DBConnection {
  val SessionRepoImpl = new SessionRepoImpl
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
          val knolid = KnolRepoImpl.createKnol(knol)
          val query_x = "create table knolsession(id int(4)primary key auto_increment,topic varchar(50),session_date date unique key,knolid int(4) references knol(id));"
          statement.executeUpdate(query_x)
          val knolx = KnolSession(1, "narayan ", "2015-04-24", knolid.get)
          SessionRepoImpl.createSession(knolx)
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
          val query_x = "drop table knolsession;"
          statement.execute(query_x)
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
  test("Create a knolx with Exception") {
    val knolx = KnolSession(1, "narayan ", "2015-04-24",1)
    assert(SessionRepoImpl.createSession(knolx) ===None)
  }
  test("Update a knolx with Exception") {
    val knolx = KnolSession(2, "narayan kumar", "2015-04-24",2)
    assert(SessionRepoImpl.update(knolx) ===0)
  }
  test("Delete a knolx with Exception") {
    assert(SessionRepoImpl.delete(2) ===0)
  }
  test("getKnolx a knolx with Exception") {
    val result =SessionRepoImpl.getKnolSession(2)
    assert(result.knolSessionId ===0)
  }
  test("getList of a knolx list with Exception") {
    SessionRepoImpl.delete(1)
    val result = SessionRepoImpl.getList()
    assert(result ===MutableList())
  }
}