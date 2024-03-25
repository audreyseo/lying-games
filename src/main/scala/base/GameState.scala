package org.audreyseo.lying
package base

import base.player.Player

import org.audreyseo.lying.base.roles.RoleType

import scala.reflect.runtime.universe._

trait GameState {
  var players: Option[Iterable[Player]]
  // Gives whether a particular team has won
  def won[A <: RoleType: TypeTag]: Boolean
  // Gives whether a particular team has lost
  def lost[A <: RoleType: TypeTag]: Boolean
  def gameSize: Int = players.size
}
