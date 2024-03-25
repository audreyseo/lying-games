package org.audreyseo.lying
package base.roles

import base.player.Player

abstract class RoleType(name: String) {
  def won(players: Iterable[Player]): Boolean
}
