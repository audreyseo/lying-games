package org.audreyseo.lying
package botc.abilities

import org.audreyseo.lying.base.operations.Action
import org.audreyseo.lying.botc.game.Grimoire

trait Ability extends Action {
  def description: String
}

trait InfoAbility extends Ability {
  def getInfo(grim: Grimoire): String
}

trait PassiveAbility extends Ability {
  def isCompliant(grim: Grimoire): String
}

trait TargetableAbility extends Ability {
  def numTargets: Int
}

