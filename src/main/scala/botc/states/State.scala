package org.audreyseo.lying
package botc.states

import botc.abilities.Ability
import botc.characters.PlayerCharacter
import org.audreyseo.lying.base.operations.Duration

sealed abstract class State {

}

case class Healthy() extends State

case class Drunkenness(d: Duration, cause: Ability) extends State
case class Poisoned(d: Duration, cause: Ability) extends State
case class Madness(d: Duration, cause: Ability, asCharacter: PlayerCharacter) extends State

