package org.audreyseo.lying
package botc.scripts
import botc.characters._
import base.roles._

object RaceToTheBottom extends NoTravelersScript {

  override def scriptName: String = "Race to the Bottom"

  override def townsfolkRoles: RoleSet[Townsfolk] = Roles(Clockmaker(), Empath(), Dreamer(), Slayer(), Courtier(), Mayor())

  override def outsiderRoles: RoleSet[Outsider] = Roles(Lunatic(), Klutz())

  override def minionRoles: RoleSet[Minion] = Roles(ScarletWoman(), Spy())

  override def demonRoles: RoleSet[Demon] = Roles(Vortox)

  override def fabledRoles: RoleSet[Fabled] = Roles(Doomsayer(), Sentinel())
}
