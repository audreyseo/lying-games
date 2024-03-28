package org.audreyseo.lying
package botc.abilities

import org.audreyseo.lying.base.operations.{Action, Timeline}
import org.audreyseo.lying.botc.characters.PlayerCharacter
import org.audreyseo.lying.botc.game.Grimoire

trait Ability extends Action {
  def description: String
  var wokeOn: List[Timeline] = List.empty

  def addWokeOn(t: Timeline): this.type = {
    wokeOn = t :: wokeOn
    this
  }

  def hasWokeOn(t: Timeline): Boolean = {
    wokeOn.contains(t)
  }

  def addWokeOn(grim: Grimoire): this.type = {
    addWokeOn(grim.getPhase)
  }
}

trait InfoAbility[A] extends Ability {
  var info: List[A] = List.empty
  def addInfo(a: A): this.type = {
    info = a :: info
    this
  }
  def getInfo(grim: Grimoire): this.type = {
    addWokeOn(grim)
    this
  }
}

trait PassiveAbility extends Ability {
  def isCompliant(grim: Grimoire): Boolean

  def makeCompliant(grim: Grimoire): this.type
}

trait TargetableAbility[A] extends Ability {
  def numTargets: Int
  var targeted: List[A] = List.empty
  def addTargeted(a: A): this.type = {
    targeted = a :: targeted
    this
  }

  def target(a: A, grim: Grimoire): this.type = {
    addWokeOn(grim)
    this
  }
}

trait GainCharacterAbility extends Ability {
  var gainedAbility: Option[PlayerCharacter] = None

  def canGainAbility: Boolean
  def gainCharacter[A <: PlayerCharacter](a: A, grim: Grimoire): this.type = {
    addWokeOn(grim)
    this
  }
}

trait TriggersOnDeathAbility extends Ability {
  def triggerOnDeath(grim: Grimoire): this.type = {
    addWokeOn(grim)
    this
  }
}
