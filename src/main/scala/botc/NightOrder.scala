package org.audreyseo.lying
package botc

import org.audreyseo.lying.base.operations.Precedence

trait NightOrder extends Precedence {
  def firstNight: Boolean
  def otherNight: Boolean
  def firstNightValue: Int
  def otherNightValue: Int

  def isFirstNight = firstNight
  def isOtherNight = otherNight

  def getPrecedence(nightNum: Int): Int =
    nightNum match {
      case 1 => firstNightValue
      case _ => otherNightValue
    }

  def compare(n: NightOrder, nightNum: Int): Int =
    this.getPrecedence(nightNum) - n.getPrecedence(nightNum)
}

object NightOrder {

  trait NoFirstNight extends NightOrder {
    def firstNight = false
    def firstNightValue = 0
  }

  trait OtherNightOnly extends NoFirstNight {
    def otherNight = true
  }


  trait NoOtherNight extends NightOrder {
    def otherNight = false
    def otherNightValue = 0
  }

  trait FirstNightOnly extends NoOtherNight {
    def firstNight = true
  }

  trait NoNights extends NoFirstNight with NoOtherNight

  trait AllNights extends NightOrder {
    def firstNight = true
    def otherNight = true
  }
}
