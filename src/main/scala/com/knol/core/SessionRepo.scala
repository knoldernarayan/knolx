package com.knol.core
import com.knol.core.impl._
import scala.collection.mutable.MutableList
/**
 * This is a SessionRepo trait which defines set of methods which is going to implement by KnolSessionImpl class
 */
trait SessionRepo {
def createSession(knol: KnolSession):Option[Int]
def update(knol: KnolSession):Int
def delete(id:Int):Int
def getKnolSession(id:Int): KnolSession
def getList():MutableList[KnolSession]
}
/**
 * This is KnolSession case class which is use for create KnolSession object
 */
case class KnolSession(val knolSessionId :Int,
val topic:String,
val date:String,
val knolId:Int)
