package org.audreyseo.lying
package botc

import org.audreyseo.lying.botc.characters.Demon
import org.audreyseo.lying.botc.characters.Minion
import org.audreyseo.lying.botc.characters.Outsider
import org.audreyseo.lying.botc.characters.Townsfolk
import org.audreyseo.lying.botc.characters.Traveler
import botc.characters.Character

import org.audreyseo.lying.botc.characters.Fabled
import org.audreyseo.lying.roles._

trait RoleSet[A <: Role] extends Set[A] {
  def getRole(s: String) = {
    this.find((a: A) => a.getName == s)
  }

  def hasRole(s: String) =
    this.exists((a: A) => a.getName == s)
}

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

  def hasFabled(): Boolean =
    fabledRoles.nonEmpty

  def hasTownsfolk(): Boolean =
    townsfolkRoles.nonEmpty

  def hasOutsiders() = outsiderRoles.nonEmpty
  def hasMinions() = minionRoles.nonEmpty
  def hasDemons() = demonRoles.nonEmpty
  def hasTravelers() = travelerRoles.nonEmpty
}
