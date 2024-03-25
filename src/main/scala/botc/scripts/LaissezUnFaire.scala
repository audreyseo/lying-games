package org.audreyseo.lying
package botc.scripts
import botc.characters._
import base.roles._

object LaissezUnFaire extends NoTravelersScript with NoFabledScript {

  override def scriptName: String = "Laissez un Faire"

  override def townsfolkRoles: RoleSet[Townsfolk] =
    Roles(Savant(),
          Artist(),
          Balloonist(),
          Amnesiac(),
          Fisherman(),
          Cannibal())

  override def outsiderRoles: RoleSet[Outsider] =
    Roles(Lunatic(), Mutant())

  override def minionRoles: RoleSet[Minion] =
    Roles(Widow(), Goblin())

  override def demonRoles: RoleSet[Demon] =
    Roles(Leviathan)
}
