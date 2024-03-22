package org.audreyseo.lying
package botc.states

import roles.Duration

import org.audreyseo.lying.botc.abilities.Ability
import org.audreyseo.lying.botc.characters.PlayerCharacter

sealed abstract class State {

}

case class Healthy() extends State

case class Drunkenness(d: Duration, cause: Ability) extends State
case class Poisoned(d: Duration, cause: Ability) extends State
case class Madness(d: Duration, cause: Ability, asCharacter: PlayerCharacter) extends State

