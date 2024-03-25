package org.audreyseo.lying
package botc.characters

import botc.abilities._
import botc.{AnyOfModifier, Unknown, ModifyOutsiders, NoOpModifier}
import base.roles.HasModifier

sealed abstract class Fabled(name: String, description: String) extends Character(name, description) {
  setRoleType(FabledType())
  setAlignment(Unknown())
}

case class Doomsayer() extends Fabled("Doomsayer", "If 4 or more players live, each living player may publicly choose (once per game) that a player of their own alignment dies.")
case class Angel() extends Fabled("Angel", "Something bad might happen to whoever is most responsible for the death of a new player.") with HasReminders.HasSomethingBad {
  addReminders(Reminders.Protect())
}
case class Buddhist() extends Fabled("Buddhist", "For the first 2 minutes of each day, veteran players may not talk.")
case class HellsLibrarian() extends Fabled("Hell's Librarian", "Something bad might happen to whoever talks when the Storyteller has asked for silence.") with HasReminders.HasSomethingBad {
  addReminder(Reminders.SomethingBad())
}
case class Revolutionary() extends Fabled("Revolutionary", "2 neighboring players are known to be the same alignment. Once per game, 1 of them registers falsely.") with HasReminders.HasUsed
case class Fiddler() extends Fabled("Fiddler", "Once per game, the Demon secretly chooses an opposing player: all players choose which of these 2 players win.")
case class Toymaker() extends Fabled("Toymaker", "The Demon may choose not to attack & must do this at least once per game. Evil players get normal starting info.") with HasReminders {
  addReminder(Reminders.FinalNightNoAttack())
}

case class Fibbin() extends Fabled("Fibbin", "Once per game, 1 good player might get incorrect information.") with HasReminders.HasUsed
case class Duchess() extends Fabled("Duchess", "Each day, 3 players may choose to visit you. At night*, each visitor learns how many visitors are evil, but 1 gets false info.") with HasReminders {
  addReminders(Reminders.Visitor(), Reminders.FalseInfo())
}
case class Sentinel() extends Fabled("Sentinel", "There might be 1 extra or 1 fewer Outsider in play.") with HasModifier {
  def mod = AnyOfModifier(ModifyOutsiders(-1), NoOpModifier(), ModifyOutsiders(1))
}
case class SpiritOfIvory() extends Fabled("Spirit of Ivory", "There can't be more than 1 extra evil player.") with HasReminders {
  addReminder(Reminders.NoExtraEvil)
}
case class Djinn() extends Fabled("Djinn", "Use the Djinn's special rule. All players know what it is.")

case class Bootlegger() extends Fabled("Bootlegger", "This script has homebrew characters or rules.")
case class Ferryman() extends Fabled("Ferryman", "On the final day, all dead players regain their vote token.")
case class Gardener() extends Fabled("Gardener", "The Storyteller assigns 1 or more players' characters.")
case class StormCatcher() extends Fabled("Storm Catcher", "Name a good character. If in play, they can only die by execution, but evil players learn which player it is.") with HasReminders {
  addReminder(Reminders.Safe)
}
