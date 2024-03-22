package org.audreyseo.lying
package botc.abilities

import roles.Action
import roles.Precedence
import roles.Time

import org.audreyseo.lying.botc.Grimoire

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



