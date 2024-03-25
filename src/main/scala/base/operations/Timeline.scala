package org.audreyseo.lying
package base.operations

import scala.reflect.runtime.universe._
// Represent a point on a timeline
trait Timeline extends Time with scala.math.Ordering[Timeline] {
  def compare[T <: Timeline](t: T): Int = {
    toInt - t.toInt
  }
  def compare(x: Timeline, y: Timeline) = x.toInt - y.toInt

  // Give any timeline a way of converting to an integer number, a Timeline should only be equal to another Timeline if their times are equal (and they're the same type)
  def toInt: Int
  def equals[T <: Timeline: TypeTag](other: T): Boolean = {
    if (base.utility.TypeUtils.typeEquivalence[this.type, T]) {
      toInt == other.toInt
    } else {
      false
    }
  }
  def nextTime: Timeline
  def lastTime: Timeline
  // Get a time that is 24 hours in the future/past
  def next24Hours: Timeline
  def last24Hours: Timeline
  def isDay: Boolean = isDuringDay
  def isNight: Boolean = isDuringNight

  def toDayNightNum: Int

  def getTimeOfDayDescriptor: String =
    if (isDay) "Day" else "Night"

  override def toString: String = {
    s"${getTimeOfDayDescriptor}($toDayNightNum)"
  }

  def isFirst: Boolean

  def isTime(t: Time): Boolean =
    if (isDay && t.isDuringDay) {
      t match {
        case EveryDay(notFirst) =>
          if (notFirst) {
            !isFirst
          } else {
            true
          }
        case o: OnceDuringDay =>
          !o.isUsed
      }
    } else if (isNight && t.isDuringNight) {
      t match {
        case EveryNight(notFirst) =>
          !notFirst || !isFirst
        case o: OnceAtNight =>
          !o.isUsed
      }
    } else {
      false
    }
}
