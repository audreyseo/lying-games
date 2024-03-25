package org.audreyseo.lying
package botc.events

import botc.BotcPlayer
import botc.characters.PlayerCharacter

abstract class Event

sealed abstract class IntransitiveEvent(subject: BotcPlayer) extends Event {
  def getSubject = subject
}

// Modeling a player receiving info
sealed abstract class InfoEvent[A](subject: BotcPlayer, info: A) extends IntransitiveEvent(subject) {
  def getInfo: A = info
}

// Model a player targeting something
sealed abstract class TransitiveEvent[A](subject: BotcPlayer, obj: A) extends IntransitiveEvent(subject) {
  def getTarget: A = obj
}


sealed abstract class MultiEvent[A](subject: BotcPlayer, objs: A*) extends IntransitiveEvent(subject) {
  def getTargets: Seq[A] = objs
}

case class Abstain(player: BotcPlayer) extends IntransitiveEvent(player)

case class GetsInfo[A](player: BotcPlayer, info: A) extends InfoEvent(player, info)

case class TargetPlayer(player: BotcPlayer, target: BotcPlayer) extends TransitiveEvent(player, target)

case class TargetCharacter(player: BotcPlayer, target: PlayerCharacter) extends TransitiveEvent(player, target)

case class TargetPlayers(player: BotcPlayer, targets: BotcPlayer*) extends MultiEvent(player, targets: _*)

case class TargetCharacters(player: BotcPlayer, targets: PlayerCharacter*) extends MultiEvent(player, targets: _*)

