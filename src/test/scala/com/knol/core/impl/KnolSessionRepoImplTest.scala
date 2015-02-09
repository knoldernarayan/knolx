package com.knol.core.impl
import com.knol.core._
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import com.knol.db.connection.DBConnection
/**
 * This is KnolSessionRepoImplTest class which is use for test all methods define in KnolSessionRepoImpl class.
 */
class KnolSessionRepoImplTest extends FunSuite with BeforeAndAfter with DBConnection{
 val SessionRepoImpl = new SessionRepoImpl
  val KnolRepoImpl = new KnolRepoImpl
  val KnolSessionRepoImpl=new KnolSessionRepoImpl
  before {
    val connection = getConnection()
    connection match {
      case Some(con) =>
        try {
          val statement = con.createStatement()
          val query = "create table knol(id int(4)primary key auto_increment,name varchar(30),email varchar(30) unique key,mobileno varchar(15));"
          val result = statement.executeUpdate(query)
          val knol = Knolder(1, "narayan ", "narayan@knoldus.com ", "9934099340")
          val knolid = KnolRepoImpl.createKnol(knol)
          val query_x = "create table knolsession(id int(4)primary key auto_increment,topic varchar(50),session_date date unique key,knolid int(4) references knol(id));"
          statement.executeUpdate(query_x)
          val knolx = KnolSession(1, "on scala ", "2015-04-24", knolid.get)
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
  test("getKnolxDetailByKnolId is") {
    val knolx = KnolSession(1, "on php ", "2015-04-23 ",1)
    SessionRepoImpl.createSession(knolx)
    assert(KnolSessionRepoImpl.getKnolxDetailByKnolId(1).iterator.size === 2)
  }
  test("getKnolDetailByKnolxId is") {
    val knolx = KnolSession(1, "on sql ", "2015-04-23 ",1)
    SessionRepoImpl.createSession(knolx)
    val result=KnolSessionRepoImpl.getKnolDetailByKnolxId(2)
    assert( result.id===1)
  }
  test("getAllKnolSessionDetail is") {
    val knolx = KnolSession(1, "on java", "2015-04-23 ",1)
    SessionRepoImpl.createSession(knolx)
    assert(KnolSessionRepoImpl.getAllKnolSessionDetail().iterator.size === 2)
  }
}