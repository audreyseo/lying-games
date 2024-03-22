package org.audreyseo.lying
package botc

import roles.Alignment

import org.audreyseo.lying.botc.characters.EvilCharacterType
import org.audreyseo.lying.botc.characters.GoodCharacterType
import org.audreyseo.lying.roles.HasAlignedWith
import org.audreyseo.lying.roles.HasAlignment
import org.audreyseo.lying.roles.RoleType

sealed abstract class BotcAlignment(name: String) extends Alignment(name) with HasAlignedWith {
  def isGood: Boolean
}

case class Good() extends BotcAlignment("Good") {
  def isGood = true
  override def isAlignedWith(a: Alignment): Boolean =
    a.isInstanceOf[Good]
  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[GoodCharacterType]
  override def isAlignedWith(h: HasAlignment): Boolean = {
    h.alignment.isInstanceOf[Good]
  }
}
case class Evil() extends BotcAlignment("Evil") {
  def isGood = false

  override def isAlignedWith(a: Alignment): Boolean =
    a.isInstanceOf[Evil]
  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[EvilCharacterType]
  override def isAlignedWith(h: HasAlignment): Boolean =
    h.alignment.isInstanceOf[Evil]
}
//
//case class Traveler(isEvil: Boolean) extends BotcAlignment(if (isEvil) "Evil" else "Good") {
//  def isGood: Boolean = !isEvil
//}

//sealed abstract class Good extends BotcAlignment("Good") {
//  def isGood = true
//}
//sealed abstract class Evil extends BotcAlignment("Evil") {
//  def isGood = false
//}
//
//case class ATownsfolk() extends Good
//case class AOutsider() extends Good
//case class AMinion() extends Evil
//case class ADemon() extends Evil
