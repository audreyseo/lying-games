package org.audreyseo.lying
package botc.characters

import roles.Alignment

sealed abstract class Traveler(alignment: Alignment, name: String, description: String) extends PlayerCharacter(name, description) {
  def roleType = TravelerType(alignment)
}

case class Scapegoat(alignment: Alignment) extends Traveler(alignment, "Scapegoat", "If a player of your alignment is executed, you might be executed instead.")
case class Gunslinger(alignment: Alignment) extends Traveler(alignment, "Gunslinger", "Each day, after the 1st vote has been tallied, you may choose a player that voted: they die.")
case class Beggar(alignment: Alignment) extends Traveler(alignment, "Beggar", "You must use a vote token to vote. If a dead player gives you theirs, you learn their alignment. You are sober and healthy.")
case class Bureaucrat(alignment: Alignment) extends Traveler(alignment, "Bureaucrat", "Each night, choose a player (not yourself): their vote counts as 3 votes tomorrow.")
case class Thief(alignment: Alignment) extends Traveler(alignment, "Thief", "Each night, choose a player (not yourself): their vote counts negatively tomorrow.")


case class Butcher(alignment: Alignment) extends Traveler(alignment, "Butcher", "Each day, after the 1st execution, you may nominate again.")
case class BoneCollector(alignment: Alignment) extends Traveler(alignment, "Bone Collector", "Once per game, at night, choose a dead player: they regain their ability until dusk.")
case class Harlot(alignment: Alignment) extends Traveler(alignment, "Harlot", "Each night*, choose a living player: if they agree, you learn their character, but you both might die.")
case class Barista(alignment: Alignment) extends Traveler(alignment, "Barista", "Each night, until dusk, 1) a player becomes sober, healthy & gets true info, or 2) their ability works twice. They learn which.")
case class Deviant(alignment: Alignment) extends Traveler(alignment, "Deviant", "If you were funny today, you cannot die by exile.")

case class Apprentice(alignment: Alignment) extends Traveler(alignment, "Apprentice", "On your 1st night, you gain a Townsfolk ability (if good) or a Minion ability (if evil).")
case class Matron(alignment: Alignment) extends Traveler(alignment, "Matron", "Each day, you may choose up to 3 sets of 2 players to swap seats. Players may not leave their seats to talk in private.")
case class Voudon(alignment: Alignment) extends Traveler(alignment, "Voudon", "Only you & the dead can vote. They don't need a vote token to do so. A 50% majority isn't required.")
case class Judge(alignment: Alignment) extends Traveler(alignment, "Judge", "Once per game, if another player nominated, you may choose to force the current execution to pass or fail.")
case class Bishop(alignment: Alignment) extends Traveler(alignment, "Bishop", "Only the Storyteller can nominate. At least 1 opposing player must be nominated each day.")
