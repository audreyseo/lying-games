package org.audreyseo.lying
package botc.characters

import base.roles.HasModifier
import botc.abilities._
import botc._

sealed abstract class Demon(name: String, description: String, first:Int =0, other: Int =0) extends EvilCharacter(name, description) with NightOrder {
  setRoleType(DemonType())
  def otherNight = other != 0
  def otherNightValue = other
  def firstNightValue = first
  def firstNight = first != 0
}


sealed abstract class KillingDemon(name: String, description: String, first:Int =0, other: Int =0) extends Demon(name, description, first=first, other=other) with HasReminders.HasDead

sealed abstract class PoisoningDemon(name: String, description: String, first:Int =0, other: Int =0) extends KillingDemon(name, description, first=first, other=other) with HasReminders.HasPoisoned
case object AlHadikhia extends Demon("Al-Hadikhia", "Each night*, choose 3 players (all players learn who): each silently chooses to live or die, but if all live, all die.", other=33) with HasReminders {
  import botc.abilities.Reminders._
  addReminders(One(), Two(), Three(), ChoseDeath(), ChoseLife())
}
case object FangGu extends KillingDemon("Fang Gu", "Each night*, choose a player: they die. The 1st Outsider this kills becomes an evil Fang Gu & you die instead. [+1 Outsider]", other=29) with HasModifier {
  def mod = ModifyOutsiders(1)
  addReminder(Reminders.Once())
}
case object Imp extends KillingDemon("Imp", "Each night*, choose a player: they die. If you kill yourself this way, a Minion becomes the Imp.", other=24)
case object Kazali extends Demon("Kazali", "Each night*, choose a player: they die. [You choose which players are which Minions. -? to +? Outsiders]")
case object Legion extends KillingDemon("Legion", "Each night*, a player might die. Executions fail if only evil voted. You register as a Minion too. [Most players are Legion]", other=23) with HasMisregistration with HasModifier {
  addReminder(Reminders.AboutToDie())
  override def misregistersAs = Set(MinionType())
  def mod: BotcModifier = ImplicationModifier(SwitchesAllAlignments, ImplicationModifier(AllEvilAre(Legion), AnyGood))
}
case object Leviathan extends Demon("Leviathan", "If more than 1 good player is executed, evil wins. All players know you are in play. After day 5, evil wins.", first=54, other=73) with HasReminders {
  import botc.abilities.Reminders._
  addReminders(Day1(), Day2(), Day3(), Day4(), Day5(), GoodPlayerExecuted())
}
case object LilMonsta extends KillingDemon("Lil' Monsta", "Each night, Minions choose who babysits Lil' Monsta's token & \"is the Demon\". A player dies each night*. [+1 Minion]", first=15, other=35) with HasModifier {
  def mod = ModifyMinions(1)
  addReminder(Reminders.IsTheDemon().setGlobal(true))
}
case object Lleech extends PoisoningDemon("Lleech", "Each night*, choose a player: they die. You start by choosing an alive player: they are poisoned - you die if (& only if) they die.", first=16, other=34)
case object NoDashii extends PoisoningDemon("No Dashii", "Each night*, choose a player: they die. Your 2 Townsfolk neighbors are poisoned.", other=30)
case object Ojo extends Demon("Ojo", "Each night*, choose a character: they die. If they are not in play, the Storyteller chooses who dies.")
case object Po extends KillingDemon("Po", "Each night*, you may choose a player: they die. If your last choice was no-one, choose 3 players tonight.", other=28) {
  addReminder(Reminders.ThreeAttacks())
}
case object Pukka extends PoisoningDemon("Pukka", "Each night, choose a player: they are poisoned. The previously poisoned player dies then becomes healthy.", first=28, other=26)
case object Riot extends Demon("Riot", "Nominees die, but may nominate again immediately (on day 3, they must). After day 3, evil wins. [All Minions are Riot]") with HasModifier {
  def mod = AllMinionsAre(Riot)
}
case object Shabaloth extends KillingDemon("Shabaloth", "Each night*, choose 2 players: they die. A dead player you chose last night might be regurgitated.", other=27) with HasReminders.HasAlive
case object Vigormortis extends PoisoningDemon("Vigormortis", "Each night*, choose a player: they die. Minions you kill keep their ability & poison 1 Townsfolk neighbor. [-1 Outsider]") with HasModifier with HasReminders.HasAbility {
  def mod = ModifyOutsiders(-1)
}
case object Vortox extends KillingDemon("Vortox", "Each night*, choose a player; they die. Townsfolk abilities yield false info. Each day, if no-one is executed, evil wins.", other=31)
case object Yaggababble extends Demon("Yaggababble","You start knowing a secret phrase. For each time you said it publicly today, a player might die.")
case object Zombuul extends KillingDemon("Zombuul", "Each night*, if no-one died today, choose a player: they die. The 1st time you die, you live but register as dead.", other=25) with HasReminders.HasDiedToday

