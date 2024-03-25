package org.audreyseo.lying
package botc.scripts

trait NoFabledScript extends Script {
  def fabledRoles = Roles()
}

trait NoTravelersScript extends Script {
  def travelerRoles = Roles()
}
