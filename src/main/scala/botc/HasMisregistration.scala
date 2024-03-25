package org.audreyseo.lying
package botc

import org.audreyseo.lying.base.roles.alignments.HasAlignedWith

trait HasMisregistration {
  def misregistersAs: Set[HasAlignedWith]
  def canMisregisterAs(alignment: HasAlignedWith): Boolean = misregistersAs.contains(alignment)
}
