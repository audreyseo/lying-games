package org.audreyseo.lying
package roles

trait HasAlignedWith {
  def isAlignedWith(a: Alignment): Boolean
  def isAlignedWith(r: RoleType): Boolean
  def isAlignedWith(h: HasAlignment): Boolean
}
