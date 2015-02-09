package com.knol.core

import scala.collection.mutable.MutableList
/**
 * This is KnolSessionRepo trait which is collection of common methods of KnolRepoImpl class and SessionRepoImpl class.
 */
trait KnolSessionRepo {
  def getKnolxDetailByKnolId(id: Int): MutableList[KnolSession]
  def getKnolDetailByKnolxId(id: Int): Knolder
  def getAllKnolSessionDetail(): MutableList[DetailKnolSession]
}
case class DetailKnolSession(knol_id: Int, knol_name: String, knol_email: String, knol_mobile: String,
                             knolx_id: Int, knolx_topic: String, knolx_date: String)
