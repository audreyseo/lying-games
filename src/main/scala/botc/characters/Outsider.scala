package org.audreyseo.lying
package botc.characters

import botc.BotcAlignment
import botc.Evil
import botc.HasMisregistration

sealed abstract class Outsider(name: String, description: String) extends GoodCharacter(name, description) {
  def roleType = OutsiderType()
}

case class Acrobat() extends Outsider("Acrobat", "Each night*, if either good living neighbour is drunk or poisoned, you die.")
case class Butler() extends Outsider("Butler", "If you died today or tonight, the Demon may choose 2 players (not another Demon) to swap characters.")
case class Barber() extends Outsider("Barber", "Each night, choose a player (not yourself): tomorrow, you may only vote if they are voting too.")
case class Damsel() extends Outsider("Damsel", "All Minions know you are in play. If a Minion publicly guesses you (once), your team loses.")
case class Drunk(townsfolk: Townsfolk) extends Outsider("Drunk", "You do not know you are the Drunk. You think you are a Townsfolk character, but you are not.")
case class Golem() extends Outsider("Golem", "You may only nominate once per game. When you do, if the nominee is not the Demon, they die.")
case class Goon() extends Outsider("Goon", "Each night, the 1st player to choose you with their ability is drunk until dusk. You become their alignment.")
case class Hatter() extends Outsider("Hatter", "If you died today or tonight, the Minion & Demon players may choose new Minion & Demon characters to be.")
case class Heretic() extends Outsider("Heretic", "Whoever wins, loses & whoever loses, wins, even if you are dead.")
case class Klutz() extends Outsider("Klutz", "When you learn that you died, publicly choose 1 alive player: if they are evil, your team loses.")
case class Lunatic() extends Outsider("Lunatic", "You think you are a Demon, but you are not. The Demon knows who you are & who you choose at night.")
case class Moonchild() extends Outsider("Moonchild", "When you learn that you died, publicly choose 1 alive player. Tonight, if it was a good player, they die.")
case class Mutant() extends Outsider("Mutant", "If you are \"mad\" about being an Outsider, you might be executed.")
case class PlagueDoctor() extends Outsider("Plague Doctor", "If you die, the Storyteller gains a not-in-play Minion ability.")
case class Politician() extends Outsider("Politician", "If you were the player most responsible for your team losing, you change alignment & win, even if dead.")
case class Puzzlemaster() extends Outsider("Puzzlemaster", "1 player is drunk, even if you die. If you guess (once) who it is, learn the Demon player, but guess wrong & get false info.")
case class Recluse() extends Outsider("Recluse", "You might register as evil & as a Minion or Demon, even if dead.") with HasMisregistration {
  override def misregistersAs: Set[BotcAlignment] = List(Evil()).toSet
}
case class Saint() extends Outsider("Saint", "If you die by execution, your team loses.")
case class Snitch() extends Outsider("Snitch", "Minions start knowing 3 not-in-play characters.")
case class Sweetheart() extends Outsider("Sweetheart", "When you die, 1 player is drunk from now on.")
case class Tinker() extends Outsider("Tinker", "You might die at any time.")
