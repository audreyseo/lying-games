package org.audreyseo.lying
package botc.registration

import base.roles.RoleType
import base.roles.alignments.{Alignment, HasAlignedWith, HasAlignment}
import botc.characters.{EvilCharacterType, GoodCharacterType}

sealed abstract class BotcAlignment(name: String) extends Alignment(name) with HasAlignedWith {
  def isGood: Boolean
}

case class Unknown() extends BotcAlignment("Unknown") {
  def isGood = false
  override def isAlignedWith(a: Alignment): Boolean =
    false
  override def isAlignedWith(r: RoleType): Boolean =
    false
  override def isAlignedWith(h: HasAlignment): Boolean = {
    false
  }
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
