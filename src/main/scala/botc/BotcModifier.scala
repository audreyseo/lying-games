package org.audreyseo.lying
package botc

import base.roles.Modifier
import botc.characters.Character
import org.audreyseo.lying.base.roles.alignments.HasAlignedWith

sealed abstract class BotcModifier extends Modifier("BotcModifier") {

}

// Mostly for combining with the AnyOfModifier
case class NoOpModifier() extends BotcModifier

case class ModifyOutsiders(delta: Int) extends BotcModifier
case class ModifyMinions(delta: Int) extends BotcModifier

case class NoMinions() extends BotcModifier
case class NoDemons() extends BotcModifier

case class NoEvil() extends BotcModifier
case class ChangeAlignment(of: HasAlignedWith, to: HasAlignedWith) extends BotcModifier

case class AddCharacter(role: Character) extends BotcModifier

case class AnyOfModifier(mods: BotcModifier*) extends BotcModifier
case class AllOfModifier(mods: BotcModifier*) extends BotcModifier {
  def getMods = mods
}

case class ImplicationModifier(mod1: BotcModifier, mod2: BotcModifier) extends BotcModifier

case object AnyGood extends BotcModifier
case class AllMinionsAre(role: Character) extends BotcModifier
case class AllEvilAre(role: Character) extends BotcModifier

case class Neighbors(to: HasAlignedWith) extends BotcModifier
case object SwitchesAllAlignments extends BotcModifier
