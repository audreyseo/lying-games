package org.audreyseo.lying
package botc.characters

import botc.abilities._
import botc.NightOrder
import org.audreyseo.lying.botc.registration.Unknown

sealed abstract class Traveler(name: String, description: String, first: Int = 0, other: Int = 0) extends PlayerCharacter(name, description) with NightOrder {
  setRoleType(TravelerType(Unknown()))
  //var roleType: BotcCharacterType = TravelerType(Unknown())
  setAlignment(Unknown())
  def otherNight = other != 0
  def otherNightValue = other
  def firstNightValue = first
  def firstNight = first != 0
}

case class Scapegoat() extends Traveler("Scapegoat", "If a player of your alignment is executed, you might be executed instead.")
case class Gunslinger() extends Traveler("Gunslinger", "Each day, after the 1st vote has been tallied, you may choose a player that voted: they die.")
case class Beggar() extends Traveler("Beggar", "You must use a vote token to vote. If a dead player gives you theirs, you learn their alignment. You are sober and healthy.")
case class Bureaucrat() extends Traveler("Bureaucrat", "Each night, choose a player (not yourself): their vote counts as 3 votes tomorrow.", first=1, other=1) with HasReminders {
  addReminder(Reminders.ThreeVotes())
}
case class Thief() extends Traveler("Thief", "Each night, choose a player (not yourself): their vote counts negatively tomorrow.", first=1, other=1) with HasReminders {
  addReminder(Reminders.NegativeVote())
}


case class Butcher() extends Traveler("Butcher", "Each day, after the 1st execution, you may nominate again.")
case class BoneCollector() extends Traveler("Bone Collector", "Once per game, at night, choose a dead player: they regain their ability until dusk.", other=1) with HasReminders.HasAbility with HasReminders.HasNoAbility
case class Harlot() extends Traveler("Harlot", "Each night*, choose a living player: if they agree, you learn their character, but you both might die.", other=1) with HasReminders.HasDead
case class Barista() extends Traveler("Barista", "Each night, until dusk, 1) a player becomes sober, healthy & gets true info, or 2) their ability works twice. They learn which.", first=1, other=1) with HasReminders {
  addReminders(Reminders.SoberAndHealthy(), Reminders.AbilityTwice())
}
case class Deviant() extends Traveler("Deviant", "If you were funny today, you cannot die by exile.")

case class Apprentice() extends Traveler("Apprentice", "On your 1st night, you gain a Townsfolk ability (if good) or a Minion ability (if evil).", first=1) with HasReminders {
  addReminder(Reminders.IsTheApprentice())
}
case class Matron() extends Traveler("Matron", "Each day, you may choose up to 3 sets of 2 players to swap seats. Players may not leave their seats to talk in private.")
case class Voudon() extends Traveler("Voudon", "Only you & the dead can vote. They don't need a vote token to do so. A 50% majority isn't required.")
case class Judge() extends Traveler("Judge", "Once per game, if another player nominated, you may choose to force the current execution to pass or fail.") with HasReminders.HasNoAbility
case class Bishop() extends Traveler("Bishop", "Only the Storyteller can nominate. At least 1 opposing player must be nominated each day.") with HasReminders {
  addReminders(Reminders.NominateEvil(), Reminders.NominateGood())
}
