package org.audreyseo.lying
package botc

import botc.utility.InvalidNumberOfPlayersError

package object characters {
  def getNumbers(numPlayers: Int): (Int, Int, Int, Int) =
    (getNumTownsfolk(numPlayers), getNumOutsiders(numPlayers), getNumMinions(numPlayers), getNumDemons(numPlayers))

  def getNumTownsfolk(numPlayers: Int): Int =
    numPlayers match {
      case 5 =>
        3
      case 6 => 3
      case 7 => 5
      case 8 => 5
      case 9 => 5
      case 10 => 7
      case 11 => 7
      case 12 => 7
      case 13 => 9
      case 14 => 9
      case 15 => 9
      case _ => throw InvalidNumberOfPlayersError(numPlayers, "Expected 5-15 players")
    }
  def getNumOutsiders(numPlayers: Int) : Int =
    numPlayers match {
      case 5 => 0
      case 6 => 1
      case 7 => 0
      case 8 => 1
      case 9 => 2
      case 10 => 0
      case 11 => 1
      case 12 => 2
      case 13 => 0
      case 14 => 1
      case 15 => 2
      case _ => throw InvalidNumberOfPlayersError(numPlayers, "Expected 5-15 players")
    }

  def getNumMinions(numPlayers: Int): Int =
    numPlayers match {
      case 5 => 1
      case 6 => 1
      case 7 => 1
      case 8 => 1
      case 9 => 1
      case 10 => 2
      case 11 => 2
      case 12 => 2
      case 13 => 3
      case 14 => 3
      case 15 => 3
    }

  def getNumDemons(numPlayers: Int): Int = 1
}
