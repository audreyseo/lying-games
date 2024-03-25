package org.audreyseo.lying
package base.operations

trait Time {
  def isDuringDay: Boolean
  def isDuringNight: Boolean
}

sealed trait DuringNight extends Time {
  def isDuringDay = false
  def isDuringNight = true
}

sealed trait DuringDay extends Time{
  def isDuringDay = true
  def isDuringNight = false
}


sealed abstract class Every(exceptFirst: Boolean) extends Time

case class EveryNight(exceptFirst: Boolean) extends Every(exceptFirst) with DuringNight
case class EveryDay(exceptFirst: Boolean) extends Every(exceptFirst) with DuringDay

sealed abstract class Once extends Time {
  var used = false
  def isUsed: Boolean = used
  def use(): this.type = {
    this.used = true
    this
  }
}

case class OnceAtNight() extends Once with DuringNight
case class OnceDuringDay() extends Once with DuringDay


