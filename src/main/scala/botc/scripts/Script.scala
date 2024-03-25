package org.audreyseo.lying
package botc.scripts

import base.roles._
import botc.characters.{Character, Demon, Fabled, Minion, Outsider, Townsfolk, Traveler}



trait Script {
  def scriptName: String
  def townsfolkRoles: RoleSet[Townsfolk]
  def outsiderRoles: RoleSet[Outsider]
  def minionRoles: RoleSet[Minion]
  def demonRoles: RoleSet[Demon]
  def travelerRoles: RoleSet[Traveler]
  def fabledRoles: RoleSet[Fabled]

  def hasRole(name: String) =
    townsfolkRoles.hasRole(name) || outsiderRoles.hasRole(name) || minionRoles.hasRole(name) || demonRoles.hasRole(name) || travelerRoles.hasRole(name) || fabledRoles.hasRole(name)

  def getRole(name: String): Option[Character] =
    if (hasRole(name)) {
      townsfolkRoles.getRole(name).orElse(outsiderRoles.getRole(name).orElse(minionRoles.getRole(name).orElse(demonRoles.getRole(name).orElse(travelerRoles.getRole(name).orElse(fabledRoles.getRole(name))))))
    } else {
      None
    }

  def hasFabled: Boolean =
    fabledRoles.nonEmpty

  def hasTownsfolk: Boolean =
    townsfolkRoles.nonEmpty

  def hasOutsiders = outsiderRoles.nonEmpty
  def hasMinions = minionRoles.nonEmpty
  def hasDemons = demonRoles.nonEmpty
  def hasTravelers = travelerRoles.nonEmpty
}
