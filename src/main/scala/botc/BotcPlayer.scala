package org.audreyseo.lying
package botc

import roles.Player

import org.audreyseo.lying.botc.abilities.Ability
import org.audreyseo.lying.botc.characters.PlayerCharacter
import org.audreyseo.lying.botc.states._
import org.audreyseo.lying.roles.Duration

abstract class BotcPlayer(name: String, character: PlayerCharacter) extends Player(name, character) {
  var drunk: Option[Drunkenness] = None
  var poisoned: Option[Poisoned] = None
  var mad: Option[Madness] = None
  def getStates: Set[State] = {
    List(drunk, poisoned, mad).foldLeft(Set.empty[State])(
      (s, o) => o match {
        case Some(opt) => s.union(Set(o))
        case None => s
      })
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

  def makeSane(): this.type ={
    this.mad = None
    this
  }
}


class BPlayer(name: String, character: PlayerCharacter) extends BotcPlayer(name, character) {
  var self_alignment = None
  var left: Option[BPlayer] = None
  var right: Option[BPlayer] = None
  var reminders: List[String] = List()

  def getReminders = reminders
  def addReminder(s: String): this.type = {
    reminders = s :: reminders
    this
  }
  def removeReminder(s: String): this.type = {
    reminders = reminders.filterNot((s1) => s1.equals(s))
    this
  }

  def assignLeft(lft: BPlayer): this.type = {
    this.left = Some(lft)
    this
  }
  def assignRight(rght: BPlayer): this.type = {
    this.right = Some(rght)
    this
  }

  def get_right = this.right.get
  def get_left = this.left.get

  def getRight(p: BPlayer => Boolean, origin: Option[BPlayer] = None): Option[BPlayer] =
    if (origin.isDefined && this.equals(origin.get)) {
      None
    } else {
      val o = if (origin.isEmpty) Some(this) else origin
      if (p(this.get_right)) {
        this.right
      } else {
        this.get_right.getRight(p, origin=o)
      }
    }

  def getLeft(p: BPlayer => Boolean, origin:Option[BPlayer] = None): Option[BPlayer] =
    if (origin.isDefined && this.equals(origin.get)) {
      None
    } else {
      val o = if (origin.isEmpty) Some(this) else origin
      if (p(this.get_left)) {
        this.left
      } else {
        this.get_left.getLeft(p, origin=o)
      }
    }

}
