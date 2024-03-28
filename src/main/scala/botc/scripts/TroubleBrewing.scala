package org.audreyseo.lying
package botc.scripts

import base.roles.RoleSet
import botc.characters._

object TroubleBrewing extends NoFabledScript {
  def demonRoles = Roles(Imp)

  override def scriptName: String = "Trouble Brewing"

  override def townsfolkRoles: RoleSet[Townsfolk] =
    Roles(Washerwoman(),Librarian(), Investigator(), Chef(), Empath(), FortuneTeller(), Undertaker(), Monk(), Ravenkeeper(), Virgin(), Slayer(), Soldier(), Mayor(),
          Clockmaker())

  override def outsiderRoles: RoleSet[Outsider] =
    Roles(Butler(), Drunk(), Recluse(), Saint())

  override def minionRoles: RoleSet[Minion] =
    Roles(Poisoner(), Spy(), ScarletWoman(),Baron())

  override def travelerRoles: RoleSet[Traveler] =
    Roles(Bureaucrat(), Thief(), Gunslinger(), Scapegoat(), Beggar())
}
