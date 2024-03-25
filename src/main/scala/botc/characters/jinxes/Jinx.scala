package org.audreyseo.lying
package botc.characters.jinxes

import botc.characters.PlayerCharacter

abstract class Jinx(c1: PlayerCharacter, c2: PlayerCharacter) {
  def fst = c1
  def snd = c2

  def contains(c: PlayerCharacter): Boolean =
    c.equals(c1) || c.equals(c2)

  def contains(c1: PlayerCharacter, c2: PlayerCharacter): Boolean =
    (fst.equals(c1) && snd.equals(c2)) || (fst.equals(c2) && snd.equals(c1))

  override def equals(other: Any): Boolean = {
    other match {
      case o: Jinx =>
        this.contains(o.fst, o.snd)
      case _ => false
    }
  }
}
