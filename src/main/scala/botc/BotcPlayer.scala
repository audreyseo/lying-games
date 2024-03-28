package org.audreyseo.lying
package botc

import base.operations.Duration
import base.player.Player
import base.roles.alignments.HasAlignedWith
import botc.abilities.{Ability, Reminders}
import botc.characters.PlayerCharacter
import botc.states._

abstract class BotcPlayer(name: String, character: PlayerCharacter) extends Player(name, character) with base.player.Metadata[botc.abilities.Reminder] {
  var drunk: Option[Drunkenness] = None
  var poisoned: Option[Poisoned] = None
  var mad: Option[Madness] = None
  var left: Option[BotcPlayer] = None
  var right: Option[BotcPlayer] = None

  def getStates: Set[State] = {
    List(drunk, poisoned, mad).foldLeft(Set.empty[State])(
      (s, o) => o match {
        case Some(opt) => s.union(Set(opt))
        case None => s
      })
  }

  override def isDead = {
    getMetadataOfType[Reminders.Dead].nonEmpty
  }

  def isDrunk = {
    getMetadataOfType[Reminders.DrunkennessReminder].nonEmpty
  }

  def isMad = {
    getMetadataOfType[Reminders.Mad].nonEmpty
  }

  def isPoisoned = {
    getMetadataOfType[Reminders.Poisoned].nonEmpty
  }

  def abilityMayWorkAbnormally = {
    isDrunk || isPoisoned
  }

  def getState: State = drunk match {
    case Some(s) => s
    case None => Healthy()
  }

  def makeDrunk(duration: Duration, cause: Ability): this.type = {
    this.drunk = Some(Drunkenness(duration, cause))
    this
  }

  def makeSober(): this.type = {
    this.drunk = None
    this
  }

  def makeHealthy(): this.type = {
    this.poisoned = None
    this
  }

  def makePoisoned(duration: Duration, cause: Ability): this.type = {
    this.poisoned = Some(Poisoned(duration, cause))
    this
  }

  def makeMad(duration: Duration, cause: Ability, asCharacter: PlayerCharacter): this.type = {
    this.mad = Some(Madness(duration, cause, asCharacter))
    this
  }

  def makeSane(): this.type = {
    this.mad = None
    this
  }

  def assignLeft(lft: BotcPlayer): this.type = {
    this.left = Some(lft)
    this
  }

  def assignRight(rght: BotcPlayer): this.type = {
    this.right = Some(rght)
    this
  }

  def hasLeft: Boolean = this.left.isDefined

  def hasRight: Boolean = this.right.isDefined

  def get_right = this.right.get

  def get_left = this.left.get

  def getRight(p: BotcPlayer => Boolean, origin: Option[BotcPlayer] = None): Option[BotcPlayer] =
    if (origin.isDefined && this.equals(origin.get)) {
      None
    } else {
      val o = if (origin.isEmpty) Some(this) else origin
      if (p(this.get_right)) {
        this.right
      } else {
        this.get_right.getRight(p, origin = o)
      }
    }

  def getLeft(p: BotcPlayer => Boolean, origin: Option[BotcPlayer] = None): Option[BotcPlayer] =
    if (origin.isDefined && this.equals(origin.get)) {
      None
    } else {
      val o = if (origin.isEmpty) Some(this) else origin
      if (p(this.get_left)) {
        this.left
      } else {
        this.get_left.getLeft(p, origin = o)
      }
    }

  def simpleToString: String = {
    s"$name[$character]"
  }

  def hasNightAction(nightNum: Int) =
    getRole match {
      case n: NightOrder =>
        n.getPrecedence(nightNum) > 0
      case _ => false
    }

  def getPrecedence(nightNum: Int) =
    getRole match {
      case n: NightOrder =>
        n.getPrecedence(nightNum)
      case _ => 0
    }

  def isTownsfolk: Boolean = getRole.isInstanceOf[botc.characters.Townsfolk]

  def isOutsider: Boolean = getRole.isInstanceOf[botc.characters.Outsider]

  def isMinion: Boolean = getRole.isInstanceOf[botc.characters.Minion]

  def isDemon: Boolean = getRole.isInstanceOf[botc.characters.Demon]

  def getPlayerCharacter: botc.characters.PlayerCharacter = getRole.asInstanceOf[botc.characters.PlayerCharacter]

  def getBotcCharacterType: botc.characters.BotcCharacterType = getRole.asInstanceOf[botc.characters.Character].roleType

  def isAlignedWith[A <: HasAlignedWith](a: A): Boolean =
    a.isAlignedWith(getRole) || a.isAlignedWith(getAlignment) || a.isAlignedWith(getBotcCharacterType)

  def canRegisterAs[A <: HasAlignedWith](a: A): Boolean =
    if (hasMisregistration) {
      canMisregisterAs(a)
    } else {
      isAlignedWith(a)
    }

  def hasMisregistration: Boolean =
    getRole match {
      case _: HasMisregistration => true
      case _ => false
    }

  def canMisregisterAs[A <: HasAlignedWith](a: A): Boolean =
    getRole match {
      case h: HasMisregistration => h.canMisregisterAs(a)
      case _ => false
    }
}


class BPlayer(name: String, character: PlayerCharacter) extends BotcPlayer(name, character) {
  //override var self_alignment = None

  var reminders: List[botc.abilities.Reminder] = List()

  def getReminders = reminders

  def addReminder(r: botc.abilities.Reminder): this.type = {
    reminders = r :: reminders
    this
  }

  def removeReminder(r: botc.abilities.Reminder): this.type = {
    reminders = reminders.filterNot(s1 => s1.equals(r))
    this
  }


  override def toString: String = {
    if (hasLeft && hasRight) {
      s"$simpleToString (left: ${get_left.simpleToString}, right: ${get_right.simpleToString})"
    } else if (hasLeft) {
      s"$simpleToString (left: ${get_left.simpleToString})"
    } else if (hasRight) {
      s"${simpleToString} (right: ${get_right.simpleToString})"
    } else {
      simpleToString
    }
  }

  override def takeAction[A](targets: A*): this.type = {
    this
  }

}
