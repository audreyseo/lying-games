package org.audreyseo.lying
package botc

trait HasMisregistration {
  def misregistersAs: Set[BotcAlignment]
  def canMisregisterAs(alignment: BotcAlignment): Boolean = misregistersAs.contains(alignment)
}
