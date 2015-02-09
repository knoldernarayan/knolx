package com.knol.core.impl
import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
import com.knol.core.KnolSession
import com.knol.core.Knolder
import org.scalatest.FunSuite
/**
 * This is SessionRepoImplTest class which is use for test all methods define in SessionRepoImpl class.
 */
class SessionRepoImplTest extends FunSuite with BeforeAndAfter with DBConnection {
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
          val knol = Knolder(1, "narayan ", "cv2vv ", "vvmvsv")
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
  test("Create a knolx") {
    val knolx = KnolSession(1, "narayan ", "2015-04-23 ",1)
    assert(SessionRepoImpl.createSession(knolx) === Some(2))
  }
  test("Update a knolx") {
    val knolx = KnolSession(1, "narayan kumar", "2015-04-23 ",1)
    assert(SessionRepoImpl.update(knolx) === 1)
  }
  test("Delete a knolx") {
    assert(SessionRepoImpl.delete(1) === 1)
  }
  test("getKnolx a knolx") {
    val result =SessionRepoImpl.getKnolSession(1)
    assert(result.knolSessionId === 1)
  }
  test("getList of a knolx list") {
    val result = SessionRepoImpl.getList()
    assert(result.iterator.size === 1)
  }
}