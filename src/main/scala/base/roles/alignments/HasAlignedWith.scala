package org.audreyseo.lying
package base.roles.alignments

import base.roles.RoleType

trait HasAlignedWith {
  def isAlignedWith(a: Alignment): Boolean
  def isAlignedWith(r: RoleType): Boolean
  def isAlignedWith(h: HasAlignment): Boolean
}
