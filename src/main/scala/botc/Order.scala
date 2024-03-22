package org.audreyseo.lying
package botc

import roles.Precedence

abstract class Order extends Precedence {
  def hasSetup : Boolean
  def firstNight: Boolean
  def otherNight: Boolean

  def getPrecedence(nightNum: Int): Int
}

trait HasFirstNight {
  var value: Int
  def firstNight = true
}

trait HasOtherNight {
  def otherNight = true
}

trait OnlyFirstNight extends HasFirstNight {
  def otherNight = false
}

trait OnlyOtherNight extends HasOtherNight {
  def firstNight = false
}

trait AllNights extends HasFirstNight with HasOtherNight

case class FirstNightOrder(value: Int) extends Order with OnlyFirstNight {
  def hasSetup = false
}

case class FirstNightSetup(value: Int) extends Order with OnlyFirstNight {
  def hasSetup = true
  def getPrecedence(nightNum: Int): Int =
    nightNum match {
      case 1 => value
      case _ => 0
    }
}

case class OtherNight(value: Int) extends Order with OnlyOtherNight {
  def hasSetup = false
  def getPrecedence(nightNum: Int): Int =
    nightNum match {
      case 1 => 0
      case _ => value
    }
}


