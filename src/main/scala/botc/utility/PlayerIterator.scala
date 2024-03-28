package org.audreyseo.lying
package botc.utility

import botc.BotcPlayer

// Iterate through players in a circular fashion
class PlayerIterator(player: BotcPlayer, clockwise: Boolean = false, cycles: Boolean = false) extends Iterator[BotcPlayer] {
  assert(player.hasLeft && player.hasRight)

  // Initialize current player to the player just before the current
  var currentPlayer = init
  val firstPlayer = init

  def init: BotcPlayer = if (clockwise) player.get_right else player.get_left
  var first = true

  override def hasNext: Boolean =
    if (clockwise) {
      if (cycles) {
        currentPlayer.hasLeft
      } else {
        currentPlayer.hasLeft && (first || !currentPlayer.get_left.equals(player))
      }

    } else {
      currentPlayer.hasRight && (cycles || (first || !currentPlayer.get_right.equals(player)))
    }

  override def next(): BotcPlayer = {
    currentPlayer = if (clockwise) currentPlayer.get_left else currentPlayer.get_right
    first = false
    currentPlayer
  }
}
