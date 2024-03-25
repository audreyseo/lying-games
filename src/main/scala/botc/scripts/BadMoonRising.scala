package org.audreyseo.lying
package botc.scripts
import botc.characters._
import base.roles._


object BadMoonRising extends NoFabledScript {

  override def scriptName: String = "Bad Moon Rising"

  override def townsfolkRoles: RoleSet[Townsfolk] = Roles(Grandmother(), Sailor(), Chambermaid(), Exorcist(), Innkeeper(), Gambler(), Gossip(), Courtier(), Professor(),Minstrel(), TeaLady(), Pacifist(), Fool())

  override def outsiderRoles: RoleSet[Outsider] = Roles(Tinker(), Moonchild(), Goon(), Lunatic())

  override def minionRoles: RoleSet[Minion] = Roles(Godfather(), DevilsAdvocate(), Assassin(), Mastermind())

  override def demonRoles: RoleSet[Demon] = Roles(Zombuul, Pukka, Shabaloth, Po)

  override def travelerRoles: RoleSet[Traveler] = Roles(Apprentice(), Matron(), Judge(), Bishop(), Voudon())
}
