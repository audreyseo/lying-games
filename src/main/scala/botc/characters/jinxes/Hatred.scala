package org.audreyseo.lying
package botc.characters.jinxes

import botc.characters.PlayerCharacter

import scala.collection.mutable

sealed abstract class HatredJinx(c1: PlayerCharacter, c2: PlayerCharacter) extends Jinx(c1, c2) {
}

case class NormalHatred(c1: PlayerCharacter, c2: PlayerCharacter) extends HatredJinx(c1, c2)

case class SpecialHatred(c1: PlayerCharacter, c2: PlayerCharacter) extends HatredJinx(c1, c2)

object HatredJinx {
  private var hatred: Set[HatredJinx] = Set.empty

  private var hatredMap: mutable.HashMap[PlayerCharacter, Set[HatredJinx]] = mutable
    .HashMap.empty

  def addToHatredMap(c: PlayerCharacter, h: HatredJinx): Unit = {
    if (!hatredMap.contains(c)) {
      hatredMap.put(c, Set.empty)
    }
    hatredMap.put(c, hatredMap.get(c).map(_ + h).get)
  }

  private def addHatred(c1: PlayerCharacter, c2: PlayerCharacter): Unit = {
    val normal = NormalHatred(c1, c2)
    hatred = hatred + normal
    addToHatredMap(c1, normal)
    addToHatredMap(c2, normal)

  }

  private def addSpecial(c1: PlayerCharacter, c2: PlayerCharacter): Unit = {
    val special = SpecialHatred(c1, c2)
    hatred = hatred + special
    addToHatredMap(c1, special)
    addToHatredMap(c2, special)
  }

  def contains(c1: PlayerCharacter, c2: PlayerCharacter): Boolean = {
    hatred.exists(h => h.contains(c1, c2))
  }

  import botc.characters._

  addHatred(Godfather(), Heretic())
  addHatred(Spy(), Damsel())
  addHatred(Spy(), Heretic())
  addHatred(Widow(), Damsel())
  addHatred(Legion, Preacher())
  addHatred(LilMonsta, Magician())
  addHatred(Riot, Exorcist())
  addHatred(Riot, FlowerGirl())
  addHatred(Riot, Minstrel())
  addSpecial(AlHadikhia, Mastermind())
  addSpecial(OrganGrinder(), Minstrel())
  addSpecial(OrganGrinder(), Preacher())
  addSpecial(Vizier(), Magician())
}
