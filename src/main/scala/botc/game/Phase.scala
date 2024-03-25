package org.audreyseo.lying
package botc.game

import base.operations.{Duration, Timeline}

object Phase {
  // Number of phases in 24 hours
  // 24 hours organized into: Night, Dawn, Talking, Nomination, Dusk
  val dayLen = 5
  object Offsets {
    val night = 1
    val dawn = 2
    val talking = 3
    val nomination = 4
    val dusk = 0
    def isDayOffset(offset: Int): Boolean =
      offset == dawn || offset == talking || offset == nomination || offset == dusk
  }
  sealed trait Phase extends Timeline {
    val offset: Int
    // Get the next phase
    override def nextTime: Timeline = intToPhase(toInt + 1)
    // Get the last phase
    override def lastTime: Timeline = intToPhase(toInt - 1)

    def nextPhase: Phase = intToPhase(toInt + 1)
    def lastPhase: Phase = intToPhase(toInt - 1)

    override def next24Hours: Timeline = intToPhase(toInt + dayLen)
    override def toInt = dayLen * (toDayNightNum - 1) + offset
    override def last24Hours: Timeline = intToPhase(toInt - dayLen)
    override def isFirst = toInt < dayLen + 1
    override def isDuringDay: Boolean = toInt > 0 && Offsets.isDayOffset(toInt % dayLen)
    override def isDuringNight: Boolean = toInt > 0 && toInt%dayLen == Offsets.night
  }
  case object NotStarted extends Phase {
    val offset = 0
    override def toDayNightNum: Int = 0
  }
  case class NightPhase(num: Int) extends Phase {
    // Enforce 1-indexing of nights
    assert(num > 0)
    val offset = 1
    override def toDayNightNum = num
  }
  sealed trait Day extends Phase
  case class Dawn(num: Int) extends Day {
    assert(num > 0)
    override def toDayNightNum: Int = num
    val offset = Offsets.dawn
  }
  case class Talking(num: Int) extends Day {
    // Enforce 1-indexing of nights
    assert(num > 0)
    override def toDayNightNum = num
    val offset = Offsets.talking
  }
  case class Nomination(num: Int) extends Day {
    val offset = Offsets.nomination
    override def toDayNightNum = num
  }

  def intToPhase(i: Int): Phase = {
    val nightDayNum = scala.math.floorDiv(i, 3) + 1
    i match {
      case 0 => NotStarted
      case _ if i % 3 == 1 => NightPhase(nightDayNum)
      case _ if i % 3 == 2 => Talking(nightDayNum)
      case _ if i % 3 == 0 => Nomination(nightDayNum)
    }
  }
}
