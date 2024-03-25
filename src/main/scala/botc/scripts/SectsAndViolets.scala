package org.audreyseo.lying
package botc.scripts

import botc.characters._
import base.roles._

object SectsAndViolets extends NoFabledScript {

  override def scriptName: String = "Sects & Violets"

  override def townsfolkRoles: RoleSet[Townsfolk] =
    Roles(Clockmaker(),
          Dreamer(),
          SnakeCharmer(),
          Mathematician(),
          FlowerGirl(),
          TownCrier(),
          Oracle(),
          Savant(),
          Seamstress(),
          Philosopher(),
          Artist(),
          Juggler(),
          Sage())

  override def outsiderRoles: RoleSet[Outsider] =
    Roles(Mutant(), Sweetheart(), Barber(), Klutz())

  override def minionRoles: RoleSet[Minion] =
    Roles(EvilTwin(), Witch(), Cerenovus(), PitHag())

  override def demonRoles: RoleSet[Demon] =
    Roles(FangGu, Vigormortis, NoDashii, Vortox)

  override def travelerRoles: RoleSet[Traveler] =
    Roles(Barista(), Harlot(), Butcher(), BoneCollector(), Deviant())
}
