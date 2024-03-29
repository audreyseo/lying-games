package org.audreyseo.lying
package botc.game

import base.Configuration
import org.audreyseo.lying.botc.characters.PlayerCharacter
import org.audreyseo.lying.botc.registration.HasMisregistration

import scala.math.random

object BotcGameConfiguration extends Configuration {
  /**
    * A value from 0.0 to 1.0, the probability of misregistering
    */
  var misregistrationThreshold = 0.0

  var shouldAlwaysMisregister: Set[HasMisregistration] = Set.empty
  var shouldNeverMisregister: Set[HasMisregistration] = Set.empty

  def setMisregistrationThreshold(threshold: Double): this.type = {
    assert(0 <= threshold && threshold <= 1)
    misregistrationThreshold = threshold
    this
  }

  def shouldMisregister(pc: PlayerCharacter): Boolean = {

  }


}
