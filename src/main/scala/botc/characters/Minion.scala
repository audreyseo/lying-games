package org.audreyseo.lying
package botc.characters

import botc.Evil
import botc.ModifyOutsiders
import roles.HasModifier

sealed abstract class Minion(name: String, description: String) extends EvilCharacter(name, description) {
  override def alignment: Evil = Evil()
  def roleType: BotcCharacterType = MinionType()
}
case class Assassin() extends Minion("Assassin", "Once per game, at night*, choose a player: they die, even if for some reason they could not.")
case class Baron() extends Minion("Baron", "There are extra Outsiders in play. [+2 Outsiders]") with HasModifier {
  def mod = ModifyOutsiders(2)
}
case class Boomdandy() extends Minion("Boomdandy", "If you are executed, all but 3 players die. 1 minute later, the player with the most players pointing at them, dies.")
case class Cerenovus() extends Minion("Cerenovus", "Each night, choose a player & a good character: they are \"mad\" they are this character tomorrow, or might be executed.")
case class DevilsAdvocate() extends Minion("Devil's Advocate", "Each night, choose a living player (different to last night): if executed tomorrow, they don't die.")
case class EvilTwin() extends Minion("Evil Twin", "You & an opposing player know each other. If the good player is executed, evil wins. Good can't win if you both live.")
case class Fearmonger() extends Minion("Fearmonger", "Each night, choose a player: if you nominate & execute them, their team loses. All players know if you choose a new player.")
case class Goblin() extends Minion("Goblin", "If you publicly claim to be the Goblin when nominated & are executed that day, your team wins.")
case class Godfather() extends Minion("Godfather", "You start knowing which Outsiders are in play. If 1 died today, choose a player tonight: they die. [-1 or +1 Outsider]")
case class Harpy() extends Minion("Harpy", "Each night, choose 2 players: tomorrow, the 1st player is mad that the 2nd is evil, or both might die.")
case class Marionette() extends Minion("Marionette", "You think you are a good character, but you are not. The Demon knows who you are. [You neighbor the Demon]")
case class Mastermind() extends Minion("Mastermind", "If the Demon dies by execution (ending the game), play for 1 more day. If a player is then executed, their team loses.")
case class Mezepheles() extends Minion("Mezepheles", "You start knowing a secret word. The 1st good player to say this word becomes evil that night.")
case class OrganGrinder() extends Minion("Organ Grinder", "All players keep their eyes closed when voting & the vote tally is secret. Votes for you only count if you vote.")
case class PitHag() extends Minion("Pit-Hag", "Each night*, choose a player & a character they become (if not in play). If a Demon is made, deaths tonight are arbitrary.")
case class Poisoner() extends Minion("Poisoner", "Each night, choose a player: they are poisoned tonight and tomorrow day.")
case class Psychopath() extends Minion("Psychopath", "Each day, before nominations, you may publicly choose a player: they die. If executed, you only die if you lose roshambo.")
case class ScarletWoman() extends Minion("Scarlet Woman", "If there are 5 or more players alive & the Demon dies, you become the Demon. (Travellers don't count)")
case class Spy() extends Minion("Spy", "Each night, you see the Grimoire. You might register as good & as a Townsfolk or Outsider, even if dead.")
case class Vizier() extends Minion("Vizier", "All players know who you are. You can not die during the day. If good voted, you may choose to execute immediately.")
case class Widow() extends Minion("Widow", "On your first night, look at the Grimoire & choose a player: they are poisoned. 1 good player knows a Widow is in play.")
case class Witch() extends Minion("Witch", "Each night, choose a player: if they nominate tomorrow, they die. If just 3 players live, you lose this ability.")

