package org.audreyseo.lying
package botc

import org.audreyseo.lying.base.operations.{Action, EveryNight, Precedence, Time}

trait NightOrder extends Precedence with Action {
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

  def goesOnNight(nightNum: Int): Boolean =
    nightNum match {
      case 1 => firstNight
      case _ => otherNight
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
  import scala.util.matching.Regex

  def everyNightButFirstRegex: Regex = "Each night\\*".r
  def everyNightRegex: Regex = "Each night".r
  def oncePerGameAtNightButFirstRegex: Regex = "Once per game, at night\\*".r
  def oncePerGameAtNightRegex: Regex = "Once per game, at night".r
  def oncePerGameDuringTheDayRegex: Regex = "Once per game, during the day".r
  def everyDayRegex: Regex = "Each day".r

  import base.operations._
  trait AddPermissibleTimesViaRegex {
    def description: String
    def permissibleTimes: Time =
      everyNightButFirstRegex.findFirstMatchIn(description) match {
        case Some(_) => EveryNight(false)
        case None =>
          everyNightRegex.findFirstMatchIn(description ) match {
            case Some(_) => EveryNight(true)
            case None =>
              oncePerGameAtNightButFirstRegex.findFirstMatchIn(description) match {
                case Some(_) => OnceAtNightExceptFirst
                case None =>
                  oncePerGameAtNightRegex.findFirstMatchIn(description) match {
                    case Some(_) => OnceAtNight()
                    case None =>
                      oncePerGameDuringTheDayRegex.findFirstMatchIn(description) match {
                        case Some(_) => OnceDuringDay()
                        case None =>
                          everyDayRegex.findFirstMatchIn(description) match {
                            case Some(_) => EveryDay(true)
                            case None => AnyTime
                          }
                      }
                  }

              }
          }
      }
  }
}
