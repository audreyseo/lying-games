package org.audreyseo.lying
package botc.characters

import base.roles.HasModifier
import botc.{AnyOfModifier, BelievesIsAlignment, Evil, Good, HasMisregistration, ModifyOutsiders, NightOrder}
import botc.abilities._

sealed abstract class Minion(name: String, description: String, first: Int = 0, other: Int = 0) extends EvilCharacter(name, description) with NightOrder {
  setAlignment(Evil())
  setRoleType(MinionType())
  def otherNight = other != 0
  def firstNight = first != 0
  def firstNightValue = first
  def otherNightValue = other
}
case class Assassin() extends Minion("Assassin", "Once per game, at night*, choose a player: they die, even if for some reason they could not.", other=36) with HasReminders.HasDead with HasReminders.HasNoAbility
case class Baron() extends Minion("Baron", "There are extra Outsiders in play. [+2 Outsiders]") with HasModifier {
  def mod = ModifyOutsiders(2)
}
case class Boomdandy() extends Minion("Boomdandy", "If you are executed, all but 3 players die. 1 minute later, the player with the most players pointing at them, dies.")
case class Cerenovus() extends Minion("Cerenovus", "Each night, choose a player & a good character: they are \"mad\" they are this character tomorrow, or might be executed.", first=25, other=15) with HasReminders.HasMad
case class DevilsAdvocate()
  extends
  Minion("Devil's Advocate",
         "Each night, choose a living player (different to last night): if executed tomorrow, they don't die.",
         first=22, other=13) with HasReminders {
  addReminder(Reminders.SurvivesExecution())
}
case class EvilTwin()
  extends
  Minion("Evil Twin",
         "You & an opposing player know each other. If the good player is executed, evil wins. Good can't win if you both live.",
         first=23) with HasReminders {
  addReminder(Reminders.Twin())
}
case class Fearmonger()
  extends
  Minion("Fearmonger",
         "Each night, choose a player: if you nominate & execute them, their team loses. All players know if you choose a new player.",
         first=26, other=17) with HasReminders {
  addReminder(Reminders.Fear())
}
case class Goblin() extends Minion("Goblin", "If you publicly claim to be the Goblin when nominated & are executed that day, your team wins.") with HasReminders {
  addReminder(Reminders.Claimed())
}
case class Godfather() extends Minion("Godfather", "You start knowing which Outsiders are in play. If 1 died today, choose a player tonight: they die. [-1 or +1 Outsider]") with HasReminders.HasDead with HasReminders.HasDiedToday with HasModifier {
  def mod = AnyOfModifier(ModifyOutsiders(-1), ModifyOutsiders(1))
}
case class Harpy() extends Minion("Harpy", "Each night, choose 2 players: tomorrow, the 1st player is mad that the 2nd is evil, or both might die.") with HasReminders.HasMad {
  addReminder(Reminders.Second)
}
case class Marionette() extends Minion("Marionette", "You think you are a good character, but you are not. The Demon knows who you are. [You neighbor the Demon]", first=12) with HasReminders with HasModifier {
  addReminder(Reminders.IsTheMarionette().setGlobal(true))
  def mod = BelievesIsAlignment(Good())
}
case class Mastermind() extends Minion("Mastermind", "If the Demon dies by execution (ending the game), play for 1 more day. If a player is then executed, their team loses.")
case class Mezepheles()
  extends
  Minion("Mezepheles",
         "You start knowing a secret word. The 1st good player to say this word becomes evil that night.",
         first=27,
         other=18) with HasReminders.HasNoAbility with HasReminders.HasTurnsEvil
case class OrganGrinder()
  extends
  Minion("Organ Grinder", "All players keep their eyes closed when voting & the vote tally is secret. Votes for you only count if you vote.")
case class PitHag()
  extends
  Minion("Pit-Hag",
         "Each night*, choose a player & a character they become (if not in play). If a Demon is made, deaths tonight are arbitrary.",
         other=16)
case class Poisoner()
  extends Minion("Poisoner",
                 "Each night, choose a player: they are poisoned tonight and tomorrow day.",
                 first=17,
                 other=7) with HasReminders.HasDead with HasReminders.HasPoisoned
case class Psychopath() extends Minion("Psychopath", "Each day, before nominations, you may publicly choose a player: they die. If executed, you only die if you lose roshambo.")
case class ScarletWoman()
  extends Minion("Scarlet Woman",
                 "If there are 5 or more players alive & the Demon dies, you become the Demon. (Travellers don't count)",
                 other=19) with HasReminders {
  addReminder(Reminders
                .Demon())
}
case class Spy()
  extends
  Minion("Spy",
         "Each night, you see the Grimoire. You might register as good & as a Townsfolk or Outsider, even if dead.",
         first=49,
         other=68) with HasMisregistration {
  def misregistersAs = Set(Good(), TownsfolkType(), OutsiderType())
}
case class Vizier()
  extends
  Minion("Vizier",
         "All players know who you are. You can not die during the day. If good voted, you may choose to execute immediately.",
         )
case class Widow() extends Minion("Widow", "On your first night, look at the Grimoire & choose a player: they are poisoned. 1 good player knows a Widow is in play.", first=18) with HasReminders.HasPoisoned {
  addReminder(Reminders.Knows().setGlobal(true))
}
case class Witch() extends Minion("Witch", "Each night, choose a player: if they nominate tomorrow, they die. If just 3 players live, you lose this ability.", first=24, other=14) with HasReminders {
  addReminder(Reminders.Cursed())
}

