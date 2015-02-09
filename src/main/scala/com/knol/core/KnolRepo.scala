package com.knol.core
import com.knol.core.impl._
import scala.collection.mutable.MutableList
/**
 * This is KnolRepo trait which defines set of method which is going to implement by KnolRepoImpl class.
 */
trait KnolRepo {
def createKnol(knol:Knolder):Option[Int]
def update(knol:Knolder):Int
def delete(id:Int):Int
def getKnol(id:Int):Knolder
def getList():MutableList[Knolder]
}
/**
 * This is Knolder's case class which is used for create knolder's object.
 */
case class Knolder(val id :Int,
val name:String,
val email:String,
val mobileNo:String)
