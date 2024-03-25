package org.audreyseo.lying
package botc.scripts

import botc.characters._

object ExtensionCord extends Script {
  def scriptName = "Extension Cord"
  def townsfolkRoles = Roles(Investigator(), Pixie(), Empath(), Dreamer(), Mathematician(), Oracle(), Monk(), Artist(), Fisherman(), Huntsman(), Soldier(), Ravenkeeper(), Cannibal())

  override def outsiderRoles = Roles(Puzzlemaster(), Recluse(), Mutant(), Damsel(), Barber())
  def minionRoles = Roles(Poisoner(), Spy(), ScarletWoman(), Boomdandy(), Marionette())
  def demonRoles =  Roles(NoDashii)
  def travelerRoles = Roles(Bishop(), BoneCollector(), Bureaucrat(), Butcher(), Matron())
  def fabledRoles = Roles(Sentinel())
}
