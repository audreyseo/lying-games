package org.audreyseo.lying
package botc.characters

import botc.Evil
import botc.Good
import roles.Alignment
import roles.HasAlignedWith
import roles.HasAlignment
import roles.RoleType

sealed abstract class BotcCharacterType(name: String) extends RoleType(name) {
}

case class FabledType() extends BotcCharacterType("Fabled")

sealed abstract class PlayerCharacterType(name: String) extends BotcCharacterType(name) with HasAlignment

sealed abstract class GoodCharacterType(name: String) extends PlayerCharacterType(name) with HasAlignedWith {
  def alignment: Alignment = Good()

  override def isAlignedWith(a: Alignment): Boolean = {
    a.isInstanceOf[Good]
  }

  override def isAlignedWith(h: HasAlignment): Boolean =
    h.alignment.isInstanceOf[Good]
}
sealed abstract class EvilCharacterType(name: String) extends PlayerCharacterType(name) with HasAlignedWith {
  def alignment: Alignment = Evil()

  override def isAlignedWith(a: Alignment): Boolean =
    a.isInstanceOf[Evil]

  override def isAlignedWith(h: HasAlignment): Boolean =
    h.isInstanceOf[Evil]
}

case class TownsfolkType() extends GoodCharacterType("Townsfolk") {
  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[TownsfolkType]
}
case class OutsiderType() extends GoodCharacterType("Outsider") {
  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[OutsiderType]
}

case class MinionType() extends EvilCharacterType("Minion") {
  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[MinionType]
}

case class DemonType() extends EvilCharacterType("Demon") {
  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[DemonType]
}

case class TravelerType(alignment: Alignment) extends BotcCharacterType("Traveler") with HasAlignedWith {
  override def isAlignedWith(h: HasAlignment): Boolean =
    h.isInstanceOf[alignment.type]

  override def isAlignedWith(a: Alignment): Boolean =
    a.isInstanceOf[alignment.type]

  override def isAlignedWith(r: RoleType): Boolean =
    r.isInstanceOf[TravelerType]
}

