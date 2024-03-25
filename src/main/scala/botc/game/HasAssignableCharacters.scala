package org.audreyseo.lying
package botc.game

import botc.characters.{Demon, Fabled, Minion, Outsider, PlayerCharacter, Townsfolk, Traveler}

import scala.reflect.{ClassTag, classTag}

trait HasAssignableCharacters {
  protected var assignableCharacters: Set[PlayerCharacter] = Set
    .empty

  def filterCharacter[A: ClassTag]: Set[A] =
    assignableCharacters
      .filter(p =>
                classTag[A].runtimeClass.isInstance(p))
      .map(p => p
        .asInstanceOf[A])

  def getTownsfolk: Set[Townsfolk] = {
    filterCharacter[Townsfolk]
  }

  def getOutsiders: Set[Outsider] =
    filterCharacter[Outsider]

  def getMinions: Set[Minion] = filterCharacter[Minion]

  def getDemons: Set[Demon] = filterCharacter[Demon]

  def getTravelers: Set[Traveler] = filterCharacter[Traveler]

  def getFabled: Set[Fabled] = filterCharacter[Fabled]
}
