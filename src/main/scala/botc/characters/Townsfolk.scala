package org.audreyseo.lying
package botc.characters

import botc.abilities._
import botc.{AddCharacter, AnyGood, ChangeAlignment, Evil, ImplicationModifier, ModifyOutsiders, NoEvil}
import base.roles.HasModifier
import botc.NightOrder
import org.audreyseo.lying.base.player.Player

sealed abstract class Townsfolk(name: String, description: String, first:Int =0, other: Int =0) extends GoodCharacter(name, description) with NightOrder {
  setRoleType(TownsfolkType())
  def otherNight = other != 0
  def otherNightValue = other
  def firstNightValue = first
  def firstNight = first != 0
}

sealed abstract class FirstNightTownsfolk(name: String, description: String, nightOrder: Int) extends Townsfolk(name, description, first=nightOrder)

sealed abstract class OtherNightTownsfolk(name: String, description: String, nightOrder: Int) extends Townsfolk(name, description, other=nightOrder)

sealed abstract class AllNightsTownsfolk(name: String, description: String, fstNight: Int, otherNights: Int) extends Townsfolk(name, description, first=fstNight, other=otherNights)

case class Alchemist() extends Townsfolk("Alchemist", "You have a not-in-play Minion ability.", first=3) with HasReminders {
  var m: Minion = _
  reminders = reminders + Reminders.IsTheAlchemist().setGlobal(true)
  def assignMinion(m: Minion): this.type = {
    this.m = m
    this
  }

}
case class Amnesiac() extends Townsfolk("Amnesiac", "You do not know what your ability is. Each day, privately guess what it is: you learn how accurate you are.", first=32, other=47) with HasReminders {
  var amnesiacDescription = ""
  reminders = reminders + Reminders.QuestionMark()
  addCharacter(this)
  def assignAmnesiacDescription(d: String): this.type = {
    amnesiacDescription = d
    this
  }
}
case class Artist() extends Townsfolk("Artist", "Once per game, during the day, privately ask the Storyteller any yes/no question.")
case class Atheist() extends Townsfolk("Atheist", "The Storyteller can break the game rules, and if executed, good wins, even if you are dead. [No evil characters]") with HasModifier {
  def mod = ImplicationModifier(NoEvil(), AnyGood)
}
case class Balloonist() extends AllNightsTownsfolk("Balloonist", "Each night, you learn 1 player of each character type, until there are no more types to learn. [+1 Outsider]", 45, 62) with HasModifier with HasReminders {
  def mod: ModifyOutsiders = ModifyOutsiders(1)
  import botc.abilities.Reminders._
  reminders = reminders ++ Set(SeenTownsfolk(), SeenOutsider(), SeenMinion(), SeenDemon(), SeenTraveler())
  addCharacter(this)
}
case class BountyHunter() extends AllNightsTownsfolk("Bounty Hunter", "You start knowing 1 evil player. If the player you know dies, you learn another evil player tonight. [1 Townsfolk is evil]", 46, 64) with HasModifier with HasReminders {
  var knownPlayers: Set[Player] = Set.empty
  def mod: ChangeAlignment = ChangeAlignment(TownsfolkType(), Evil())
  reminders = reminders + Reminders.Known()
  addCharacter(this)

  def addKnown(p: Player) : this.type = {
    knownPlayers = knownPlayers + p
    this
  }
}

case class Cannibal() extends Townsfolk("Cannibal", "You have the ability of the recently killed executee. If they are evil, you are poisoned until a good player dies by execution.") with HasReminders.HasDiedToday with HasReminders.HasPoisoned {
  addCharacter(this)
}
case class Chambermaid() extends AllNightsTownsfolk("Chambermaid", "Each night, choose 2 alive players (not yourself): you learn how many woke tonight due to their ability.", 51, 70)

case class Chef() extends FirstNightTownsfolk("Chef", "You start knowing how many pairs of evil players there are.", 36)

case class Choirboy() extends OtherNightTownsfolk("Choirboy", "If the Demon kills the King, you learn which player is the Demon. [+the King]", 4) with HasModifier {
  def mod: AddCharacter = AddCharacter(King())
}

case class Clockmaker() extends Townsfolk("Clockmaker", "You start knowing how many steps from the Demon to its nearest Minion.", first=41)

case class Courtier() extends AllNightsTownsfolk("Courtier", "Once per game, at night, choose a character: they are drunk for 3 nights & 3 days.", 19, 8) with HasReminders.HasNoAbility {
  import botc.abilities.Reminders._
  reminders = reminders ++ Set(Drunk3(), Drunk2(), Drunk1())
  addCharacter(this)
}
case class CultLeader() extends AllNightsTownsfolk("Cult Leader", "Each night, you become the alignment of an alive neighbour. If all good players choose to join your cult, your team wins.", 48, 66)
case class Dreamer() extends AllNightsTownsfolk("Dreamer", "Each night, choose a player (not yourself or Travellers): you learn 1 good & 1 evil character, 1 of which is correct.", 42, 56)
case class Empath() extends AllNightsTownsfolk("Empath", "Each night, you learn how many of your 2 alive neighbors are evil.", 37, 53)

case class Engineer() extends AllNightsTownsfolk("Engineer", "Once per game, at night, choose which Minions or which Demon is in play.", 13, 5) with HasReminders.HasNoAbility {
  addCharacter(this)
}
case class Exorcist() extends Townsfolk("Exorcist", "Each night*, choose a player (different to last night): the Demon, if chosen, learns who you are then doesn't wake tonight.", other=21) with HasReminders {
  reminders = reminders + Reminders.Chosen()
  addCharacter(this)
}
case class Farmer() extends Townsfolk("Farmer", "If you die at night, an alive good player becomes a Farmer.", other=48)
case class Fisherman() extends Townsfolk("Fisherman", "Once per game, during the day, visit the Storyteller for some advice to help your team win.") with HasReminders.HasNoAbility
case class FlowerGirl() extends Townsfolk("Flower Girl", "Each night*, you learn if a Demon voted today.", other=57) with HasReminders {
  reminders = reminders ++ Set(Reminders.DemonVoted(), Reminders.DemonDidntVote())
  addCharacter(this)
}
case class Fool() extends Townsfolk("Fool", "The first time you die, you don't.") with HasReminders.HasNoAbility
case class FortuneTeller() extends Townsfolk("Fortune Teller", "Each night, choose 2 players: you learn if either is a Demon. There is a good player that registers as a Demon to you.", first=38, other=54) with HasReminders {
  reminders = reminders + Reminders.RedHerring()
  addCharacter(this)
}
case class Gambler() extends Townsfolk("Gambler", "Each night*, choose a player & guess their character: if you guess wrong, you die.", other=10) with HasReminders.HasDead {
  addCharacter(this)
}
case class General() extends Townsfolk("General", "Each night, you learn which alignment the Storyteller believes is winning: good, evil, or neither.", first=50, other=69)
case class Gossip() extends Townsfolk("Gossip", "Each day, you may make a public statement. Tonight, if it was true, a player dies.", other=38) with HasReminders.HasDead {
  addCharacter(this)
}
case class Grandmother() extends Townsfolk("Grandmother", "You start knowing a good player & their character. If the Demon kills them, you die too.", first=40, other=51) with HasReminders {
  var grandchild: Player = _
  reminders = reminders + Reminders.Grandchild()
  addCharacter(this)
  def assignGrandchild(p: Player): this.type = {
    grandchild = p
    this
  }
}
case class HighPriestess() extends Townsfolk("High Priestess", "Each night, learn which player the Storyteller believes you should talk to most.")
case class Huntsman() extends Townsfolk("Huntsman", "Once per game, at night, choose a living player: the Damsel, if chosen, becomes a not-in-play Townsfolk. [+the Damsel]", first=30, other=45) with HasModifier with HasReminders.HasNoAbility {
  def mod = AddCharacter(Damsel())
  addCharacter(this)
}
case class Innkeeper() extends Townsfolk("Innkeeper", "Each night*, choose 2 players: they can't die tonight, but 1 is drunk until dusk.", other=9) with HasReminders.HasProtected with HasReminders.HasDrunk {
  addCharacter(this)
}
case class Investigator() extends Townsfolk("Investigator", "You start knowing that 1 of 2 players is a particular Minion.", first=35) with HasReminders.HasWrong {
  addReminder(Reminders.Minion())
  addCharacter(this)
}
case class Juggler() extends Townsfolk("Juggler", "On your 1st day, publicly guess up to 5 players' characters. That night, you learn how many you got correct.", other=61) with HasReminders {
  addReminder(Reminders.Correct())
}

case class King() extends Townsfolk("King", "Each night, if the dead outnumber the living, you learn 1 alive character. The Demon knows who you are.", first=10, other=63)
case class Knight() extends Townsfolk("Knight", "You start knowing 2 players that are not the Demon.")
case class Librarian() extends Townsfolk("Librarian", "You start knowing that 1 of 2 players is a particular Outsider. (Or that zero are in play.)", first=34) with HasReminders.HasWrong {
  addReminder(Reminders.Outsider())
}
case class Lycanthrope() extends Townsfolk("Lycanthrope", "Each night*, choose an alive player: if good, they die, but they are the only player that can die tonight.", other=22) with HasReminders.HasDead
case class Magician() extends Townsfolk("Magician", "The Demon thinks you are a Minion. Minions think you are a Demon.", first=5)
case class Mathematician() extends Townsfolk("Mathematician", "Each night, you learn how many players' abilities worked abnormally (since dawn) due to another character's ability.", first=52, other=71) with HasReminders {
  addReminder(Reminders.Abnormal())
}
case class Mayor() extends Townsfolk("Mayor", "If only 3 players live & no execution occurs, your team wins. If you die at night, another player might die instead.")
case class Minstrel() extends Townsfolk("Minstrel", "When a Minion dies by execution, all other players (except Travellers) are drunk until dusk tomorrow.") with HasReminders {
  addReminder(Reminders.EveryoneDrunk())
}
case class Monk() extends Townsfolk("Monk", "Each night*, choose a player (not yourself): they are safe from the Demon tonight.", other=12) with HasReminders.HasProtected
case class Nightwatchman() extends Townsfolk("Nightwatchman", "Once per game, at night, choose a player: they learn who you are.", first=47, other=65) with HasReminders.HasNoAbility
case class Noble() extends Townsfolk("Noble", "You start knowing 3 players, 1 and only 1 of which is evil.", first=44) with HasReminders {
  addReminder(Reminders.Seen())
}
case class Oracle() extends Townsfolk("Oracle", "Each night*, you learn how many dead players are evil.", other=59)
case class Pacifist() extends Townsfolk("Pacifist", "Executed good players might not die.")
case class Philosopher() extends Townsfolk("Philosopher", "Once per game, at night, choose a good character: gain that ability. If this character is in play, they are drunk.", first=2, other=2) with HasReminders.HasDrunk {
  addReminder(Reminders.IsThePhilosopher())
}
case class Pixie() extends Townsfolk("Pixie", "You start knowing 1 in-play Townsfolk. If you were mad that you were this character, you gain their ability when they die.", first=29) with HasReminders.HasAbility with HasReminders.HasMad
case class PoppyGrower() extends Townsfolk("Poppy Grower", "Minions & Demons do not know each other. If you die, they learn who each other are that night.", first=4, other=3) with HasReminders {
  addReminder(Reminders.EvilWakes())
}
case class Preacher() extends Townsfolk("Preacher", "Each night, choose a player: a Minion, if chosen, learns this. All chosen Minions have no ability.", first=14, other=6) with HasReminders {
  addReminder(Reminders.AtASermon())
}
case class Professor() extends Townsfolk("Professor", "Once per game, at night*, choose a dead player: if they are a Townsfolk, they are resurrected.", other=43) with HasReminders.HasAlive with HasReminders.HasNoAbility
case class Ravenkeeper() extends Townsfolk("Ravenkeeper", "If you die at night, you are woken to choose a player: you learn their character.", other=52)
case class Sage() extends Townsfolk("Sage", "If the Demon kills you, you learn that it is 1 of 2 players.", other=42)
case class Sailor() extends Townsfolk("Sailor", "Each night, choose an alive player: either you or they are drunk until dusk. You can't die.", first=11, other=4) with HasReminders.HasDrunk
case class Savant() extends Townsfolk("Savant", "Each day, you may visit the Storyteller to learn 2 things in private: 1 is true & 1 is false.")
case class Seamstress() extends Townsfolk("Seamstress", "Once per game, at night, choose 2 players (not yourself): you learn if they are the same alignment.", first=43, other=60) with HasReminders.HasNoAbility
case class Shugenja() extends Townsfolk("Shugenja", "You start knowing if your closest evil player is clockwise or anti-clockwise. If equidistant, this info is arbitrary.")
case class Slayer() extends Townsfolk("Slayer", "Once per game, during the day, publicly choose a player: if they are the Demon, they die.") with HasReminders.HasNoAbility
case class SnakeCharmer() extends Townsfolk("Snake Charmer", "Each night, choose an alive player: a chosen Demon swaps characters & alignments with you & is then poisoned.", first=20, other=11) with HasReminders.HasPoisoned
case class Soldier() extends Townsfolk("Soldier", "You are safe from the Demon.")
case class Steward() extends Townsfolk("Steward", "You start knowing 1 good player.")
case class TeaLady() extends Townsfolk("Tea Lady", "If both your alive neighbors are good, they can't die.") with HasReminders {
  addReminder(Reminders.CannotDie())
}
case class TownCrier() extends Townsfolk("Town Crier", "Each night*, you learn if a Minion nominated today.", other=58) with HasReminders {
  addReminders(Reminders.MinionNominated(), Reminders.MinionNotNominated())
}
case class Undertaker() extends Townsfolk("Undertaker", "Each night*, you learn which character died by execution today.", other=55) with HasReminders {
  addReminder(Reminders.Executed())
}
case class VillageIdiot() extends Townsfolk("Village Idiot", "Each night, choose a player: you learn their alignment. [+0 to +2 Village Idiots. 1 of the extras is drunk]")
case class Virgin() extends Townsfolk("Virgin", "The 1st time you are nominated, if the nominator is a Townsfolk, they are executed immediately.") with HasReminders.HasNoAbility
case class Washerwoman() extends Townsfolk("Washerwoman", "You start knowing that 1 of 2 players is a particular Townsfolk.", first=33) with HasReminders.HasWrong {
  addReminder(Reminders.Townsfolk())
}

