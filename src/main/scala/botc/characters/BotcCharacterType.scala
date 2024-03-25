package org.audreyseo.lying
package botc.characters

import botc.{BotcPlayer, Evil, Good}
import base.roles.RoleType

import org.audreyseo.lying.base.player.Player
import org.audreyseo.lying.base.roles.alignments.{Alignment, HasAlignedWith, HasAlignment}

sealed abstract class BotcCharacterType(name: String) extends RoleType(name) {
}

case class FabledType() extends BotcCharacterType("Fabled") {

  override def won(players: Iterable[Player]): Boolean =
    false
}

sealed abstract class PlayerCharacterType(name: String) extends BotcCharacterType(name) with HasAlignment

sealed abstract class GoodCharacterType(name: String) extends PlayerCharacterType(name) with HasAlignedWith {
  setAlignment(Good())

  override def isAlignedWith(a: Alignment): Boolean = {
    a
      .isInstanceOf[Good]
  }

  override def isAlignedWith(h: HasAlignment): Boolean =
    h
      .alignment
      .isInstanceOf[Good]

  override def won(players: Iterable[Player]): Boolean =
    GoodCharacterType.won(players)
}

object GoodCharacterType {
  def won(players: Iterable[Player]): Boolean =
  {
    players match {
      case ps: Iterable[BotcPlayer] =>
        ps
          .forall(p =>
                    p
                      .getRole match {
                      case c: botc.characters.Character =>
                        c
                          .roleType match {
                          case DemonType() =>
                            p
                              .isDead
                          case _ => true
                        }
                    }
                  )
      case _ => false
    }
  }
}

sealed abstract class EvilCharacterType(name: String) extends PlayerCharacterType(name) with HasAlignedWith {
  setAlignment(Evil())

  override def isAlignedWith(a: Alignment): Boolean =
    a
      .isInstanceOf[Evil]

  override def isAlignedWith(h: HasAlignment): Boolean =
    h
      .isInstanceOf[Evil]

  override def won(players: Iterable[Player]) =
    EvilCharacterType.won(players)
}

object EvilCharacterType {
  def won(players: Iterable[Player]): Boolean = {
    players match {
      case ps: Iterable[BotcPlayer] =>
        val alive = ps
          .filter(p => !p
            .isDead && (p.getRole match {
            case c: botc.characters.Character =>
              c.roleType match {
                case TravelerType(_) => false
                case _ => true
              }
          }))
          .toList
        alive
          .size <= 2 &&
          alive
            .exists(
              p =>
                p
                  .getRole match {
                  case c: botc.characters.Character =>
                    c
                      .roleType match {
                      case DemonType() =>
                        true
                      case _ => false
                    }
                }
              )
    }
  }
}

case class TownsfolkType() extends GoodCharacterType("Townsfolk") {
  override def isAlignedWith(r: RoleType): Boolean =
    r
      .isInstanceOf[TownsfolkType]
}

case class OutsiderType() extends GoodCharacterType("Outsider") {
  override def isAlignedWith(r: RoleType): Boolean =
    r
      .isInstanceOf[OutsiderType]
}

case class MinionType() extends EvilCharacterType("Minion") {
  override def isAlignedWith(r: RoleType): Boolean =
    r
      .isInstanceOf[MinionType]
}

case class DemonType() extends EvilCharacterType("Demon") {
  override def isAlignedWith(r: RoleType): Boolean =
    r
      .isInstanceOf[DemonType]
}

case class TravelerType(alignment: Alignment) extends BotcCharacterType("Traveler") with HasAlignedWith {
  override def isAlignedWith(h: HasAlignment): Boolean =
    h
      .isInstanceOf[alignment.type]

  override def isAlignedWith(a: Alignment): Boolean =
    a
      .isInstanceOf[alignment.type]

  override def isAlignedWith(r: RoleType): Boolean =
    r
      .isInstanceOf[TravelerType]

  override def won(players: Iterable[Player]): Boolean = {
    false
  }
}

