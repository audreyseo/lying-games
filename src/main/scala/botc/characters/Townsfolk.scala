package org.audreyseo.lying
package botc.characters

import botc.AddCharacter
import botc.ChangeAlignment
import botc.Evil
import botc.ModifyOutsiders
import roles.HasModifier
import roles.Player

sealed abstract class Townsfolk(name: String, description: String) extends GoodCharacter(name, description) {
  def roleType: BotcCharacterType = TownsfolkType()
}
case class Alchemist(m: Minion) extends Townsfolk("Alchemist", "You have a not-in-play Minion ability.")
case class Amnesiac(d: String) extends Townsfolk("Amnesiac", "You do not know what your ability is. Each day, privately guess what it is: you learn how accurate you are.")
case class Artist() extends Townsfolk("Artist", "Once per game, during the day, privately ask the Storyteller any yes/no question.")
case class Atheist() extends Townsfolk("Atheist", "The Storyteller can break the game rules, and if executed, good wins, even if you are dead. [No evil characters]")
case class Balloonist() extends Townsfolk("Balloonist", "Each night, you learn 1 player of each character type, until there are no more types to learn. [+1 Outsider]") with HasModifier {
  def mod: ModifyOutsiders = ModifyOutsiders(1)
}
case class BountyHunter(t: Player) extends Townsfolk("Bounty Hunter", "You start knowing 1 evil player. If the player you know dies, you learn another evil player tonight. [1 Townsfolk is evil]") with HasModifier {
  def mod: ChangeAlignment = ChangeAlignment(TownsfolkType(), Evil(), t)
}

case class Cannibal() extends Townsfolk("Cannibal", "You have the ability of the recently killed executee. If they are evil, you are poisoned until a good player dies by execution.")
case class Chambermaid() extends Townsfolk("Chambermaid", "Each night, choose 2 alive players (not yourself): you learn how many woke tonight due to their ability.")

case class Chef() extends Townsfolk("Chef", "You start knowing how many pairs of evil players there are.")

case class Choirboy() extends Townsfolk("Choirboy", "If the Demon kills the King, you learn which player is the Demon. [+the King]") with HasModifier {
  def mod: AddCharacter = AddCharacter(King())
}

case class Clockmaker() extends Townsfolk("Clockmaker", "You start knowing how many steps from the Demon to its nearest Minion.")

case class Courtier() extends Townsfolk("Courtier", "Once per game, at night, choose a character: they are drunk for 3 nights & 3 days.")
case class CultLeader() extends Townsfolk("Cult Leader", "Each night, you become the alignment of an alive neighbour. If all good players choose to join your cult, your team wins.")
case class Dreamer() extends Townsfolk("Dreamer", "Each night, choose a player (not yourself or Travellers): you learn 1 good & 1 evil character, 1 of which is correct.")
case class Empath() extends Townsfolk("Empath", "Each night, you learn how many of your 2 alive neighbors are evil.")

case class Engineer() extends Townsfolk("Engineer", "Once per game, at night, choose which Minions or which Demon is in play.")
case class Exorcist() extends Townsfolk("Exorcist", "Each night*, choose a player (different to last night): the Demon, if chosen, learns who you are then doesn't wake tonight.")
case class Farmer() extends Townsfolk("Farmer", "If you die at night, an alive good player becomes a Farmer.")
case class Fisherman() extends Townsfolk("Fisherman", "Once per game, during the day, visit the Storyteller for some advice to help your team win.")
case class FlowerGirl() extends Townsfolk("Flower Girl", "Each night*, you learn if a Demon voted today.")
case class Fool() extends Townsfolk("Fool", "The first time you die, you don't.")
case class FortuneTeller() extends Townsfolk("Fortune Teller", "Each night, choose 2 players: you learn if either is a Demon. There is a good player that registers as a Demon to you.")
case class Gambler() extends Townsfolk("Gambler", "Each night*, choose a player & guess their character: if you guess wrong, you die.")
case class General() extends Townsfolk("General", "Each night, you learn which alignment the Storyteller believes is winning: good, evil, or neither.")
case class Gossip() extends Townsfolk("Gossip", "Each day, you may make a public statement. Tonight, if it was true, a player dies.")
case class Grandmother(p: Player) extends Townsfolk("Grandmother", "You start knowing a good player & their character. If the Demon kills them, you die too.")
case class HighPriestess() extends Townsfolk("High Priestess", "Each night, learn which player the Storyteller believes you should talk to most.")
case class Huntsman() extends Townsfolk("Huntsman", "Once per game, at night, choose a living player: the Damsel, if chosen, becomes a not-in-play Townsfolk. [+the Damsel]") with HasModifier {
  def mod = AddCharacter(Damsel())
}
case class Innkeeper() extends Townsfolk("Innkeeper", "Each night*, choose 2 players: they can't die tonight, but 1 is drunk until dusk.")
case class Investigator() extends Townsfolk("Investigator", "You start knowing that 1 of 2 players is a particular Minion.")
case class Juggler() extends Townsfolk("Juggler", "On your 1st day, publicly guess up to 5 players' characters. That night, you learn how many you got correct.")

case class King() extends Townsfolk("King", "Each night, if the dead outnumber the living, you learn 1 alive character. The Demon knows who you are.")
case class Knight() extends Townsfolk("Knight", "You start knowing 2 players that are not the Demon.")
case class Librarian() extends Townsfolk("Librarian", "You start knowing that 1 of 2 players is a particular Outsider. (Or that zero are in play.)")
case class Lycanthrope() extends Townsfolk("Lycanthrope", "Each night*, choose an alive player: if good, they die, but they are the only player that can die tonight.")
case class Magician() extends Townsfolk("Magician", "The Demon thinks you are a Minion. Minions think you are a Demon.")
case class Mathematician() extends Townsfolk("Mathematician", "Each night, you learn how many players' abilities worked abnormally (since dawn) due to another character's ability.")
case class Mayor() extends Townsfolk("Mayor", "If only 3 players live & no execution occurs, your team wins. If you die at night, another player might die instead.")
case class Minstrel() extends Townsfolk("Minstrel", "When a Minion dies by execution, all other players (except Travellers) are drunk until dusk tomorrow.")
case class Monk() extends Townsfolk("Monk", "Each night*, choose a player (not yourself): they are safe from the Demon tonight.")
case class Nightwatchman() extends Townsfolk("Nightwatchman", "Once per game, at night, choose a player: they learn who you are.")
case class Noble() extends Townsfolk("Noble", "You start knowing 3 players, 1 and only 1 of which is evil.")
case class Oracle() extends Townsfolk("Oracle", "Each night*, you learn how many dead players are evil.")
case class Pacifist() extends Townsfolk("Pacifist", "Executed good players might not die.")
case class Philosopher() extends Townsfolk("Philosopher", "Once per game, at night, choose a good character: gain that ability. If this character is in play, they are drunk.")
case class Pixie() extends Townsfolk("Pixie", "You start knowing 1 in-play Townsfolk. If you were mad that you were this character, you gain their ability when they die.")
case class PoppyGrower() extends Townsfolk("Poppy Grower", "Minions & Demons do not know each other. If you die, they learn who each other are that night.")
case class Preacher() extends Townsfolk("Preacher", "Each night, choose a player: a Minion, if chosen, learns this. All chosen Minions have no ability.")
case class Professor() extends Townsfolk("Professor", "Once per game, at night*, choose a dead player: if they are a Townsfolk, they are resurrected.")
case class Ravenkeeper() extends Townsfolk("Ravenkeeper", "If you die at night, you are woken to choose a player: you learn their character.")
case class Sage() extends Townsfolk("Sage", "If the Demon kills you, you learn that it is 1 of 2 players.")
case class Sailor() extends Townsfolk("Sailor", "Each night, choose an alive player: either you or they are drunk until dusk. You can't die.")
case class Savant() extends Townsfolk("Savant", "Each day, you may visit the Storyteller to learn 2 things in private: 1 is true & 1 is false.")
case class Seamstress() extends Townsfolk("Seamstress", "Once per game, at night, choose 2 players (not yourself): you learn if they are the same alignment.")
case class Shugenja() extends Townsfolk("Shugenja", "You start knowing if your closest evil player is clockwise or anti-clockwise. If equidistant, this info is arbitrary.")
case class Slayer() extends Townsfolk("Slayer", "Once per game, during the day, publicly choose a player: if they are the Demon, they die.")
case class SnakeCharmer() extends Townsfolk("Snake Charmer", "Each night, choose an alive player: a chosen Demon swaps characters & alignments with you & is then poisoned.")
case class Soldier() extends Townsfolk("Soldier", "You are safe from the Demon.")
case class Steward() extends Townsfolk("Steward", "You start knowing 1 good player.")
case class TeaLady() extends Townsfolk("Tea Lady", "If both your alive neighbors are good, they can't die.")
case class TownCrier() extends Townsfolk("Town Crier", "Each night*, you learn if a Minion nominated today.")
case class Undertaker() extends Townsfolk("Undertaker", "Each night*, you learn which character died by execution today.")
case class VillageIdiot() extends Townsfolk("Village Idiot", "Each night, choose a player: you learn their alignment. [+0 to +2 Village Idiots. 1 of the extras is drunk]")
case class Virgin() extends Townsfolk("Virgin", "The 1st time you are nominated, if the nominator is a Townsfolk, they are executed immediately.")
case class Washerwoman() extends Townsfolk("Washerwoman", "You start knowing that 1 of 2 players is a particular Townsfolk.")

