package org.audreyseo.lying
package botc.characters

import botc.Evil
import botc.ModifyMinions
import botc.ModifyOutsiders
import roles.HasModifier

sealed abstract class Demon(name: String, description: String) extends EvilCharacter(name, description) {
  override def alignment: Evil = Evil()

  override def roleType: BotcCharacterType = DemonType()
}
case class AlHadikhia() extends Demon("Al-Hadikhia", "Each night*, choose 3 players (all players learn who): each silently chooses to live or die, but if all live, all die.")
case class FangGu() extends Demon("Fang Gu", "Each night*, choose a player: they die. The 1st Outsider this kills becomes an evil Fang Gu & you die instead. [+1 Outsider]") with HasModifier {
  def mod = ModifyOutsiders(1)
}
case class Imp() extends Demon("Imp", "Each night*, choose a player: they die. If you kill yourself this way, a Minion becomes the Imp.")
case class Kazali() extends Demon("Kazali", "Each night*, choose a player: they die. [You choose which players are which Minions. -? to +? Outsiders]")
case class Legion() extends Demon("Legion", "Each night*, a player might die. Executions fail if only evil voted. You register as a Minion too. [Most players are Legion]")
case class Leviathan() extends Demon("Leviathan", "If more than 1 good player is executed, evil wins. All players know you are in play. After day 5, evil wins.")
case class LilMonsta() extends Demon("Lil' Monsta", "Each night, Minions choose who babysits Lil' Monsta's token & \"is the Demon\". A player dies each night*. [+1 Minion]") with HasModifier {
  def mod = ModifyMinions(1)
}
case class Lleech() extends Demon("Lleech", "Each night*, choose a player: they die. You start by choosing an alive player: they are poisoned - you die if (& only if) they die.")
case class NoDashii() extends Demon("No Dashii", "Each night*, choose a player: they die. Your 2 Townsfolk neighbors are poisoned.")
case class Ojo() extends Demon("Ojo", "Each night*, choose a character: they die. If they are not in play, the Storyteller chooses who dies.")
case class Po() extends Demon("Po", "Each night*, you may choose a player: they die. If your last choice was no-one, choose 3 players tonight.")
case class Pukka() extends Demon("Pukka", "Each night, choose a player: they are poisoned. The previously poisoned player dies then becomes healthy.")
case class Riot() extends Demon("Riot", "Nominees die, but may nominate again immediately (on day 3, they must). After day 3, evil wins. [All Minions are Riot]")
case class Shabaloth() extends Demon("Shabaloth", "Each night*, choose 2 players: they die. A dead player you chose last night might be regurgitated.")
case class Vigormortis() extends Demon("Vigormortis", "Each night*, choose a player: they die. Minions you kill keep their ability & poison 1 Townsfolk neighbor. [-1 Outsider]") with HasModifier {
  def mod = ModifyOutsiders(-1)
}
case class Vortox() extends Demon("Vortox", "Each night*, choose a player; they die. Townsfolk abilities yield false info. Each day, if no-one is executed, evil wins.")
case class Yaggababble() extends Demon("Yaggababble","You start knowing a secret phrase. For each time you said it publicly today, a player might die.")
case class Zombuul() extends Demon("Zombuul", "Each night*, if no-one died today, choose a player: they die. The 1st time you die, you live but register as dead.")

