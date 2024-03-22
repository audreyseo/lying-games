package org.audreyseo.lying
package botc

import roles.Modifier

import org.audreyseo.lying.botc.characters.Character
import org.audreyseo.lying.roles.HasAlignedWith
import org.audreyseo.lying.roles.Player

sealed abstract class BotcModifier extends Modifier("BotcModifier") {

}

// Mostly for combining with the AnyOfModifier
case class NoOpModifier() extends BotcModifier

case class ModifyOutsiders(delta: Int) extends BotcModifier
case class ModifyMinions(delta: Int) extends BotcModifier

case class NoMinions() extends BotcModifier
case class NoDemons() extends BotcModifier

case class NoEvil() extends BotcModifier
case class ChangeAlignment(of: HasAlignedWith, to: HasAlignedWith, target: Player) extends BotcModifier

case class AddCharacter(role: Character) extends BotcModifier

case class AnyOfModifier(mods: BotcModifier*) extends BotcModifier

