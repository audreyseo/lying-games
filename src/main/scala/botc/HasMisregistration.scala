package org.audreyseo.lying
package botc

import base.roles.alignments.HasAlignedWith
import botc.characters.PlayerCharacter

trait HasMisregistration {
  def misregistersAs: Set[HasAlignedWith]
  def canMisregisterAs(alignment: HasAlignedWith): Boolean = misregistersAs.contains(alignment)
  def canMisregisterAs(character: botc.characters.Character): Boolean =
    character match {
      case t: PlayerCharacter =>
        canMisregisterAs(t.getBotcAlignment) || canMisregisterAs(t.getRoleType)
      case _ => false
    }
}
