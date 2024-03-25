package org.audreyseo.lying
package botc.scripts

import base.roles.{Role,RoleSet}

class Roles[A <: Role](elmts: A*) extends RoleSet[A] {
  private def internal = elmts.toSet
  def iterator = internal.iterator
  def contains(elem: A) : Boolean = internal.contains(elem)

  override def incl(elem: A): Set[A] = internal.incl(elem)

  override def excl(elem: A): Set[A] = internal.excl(elem)
}

object Roles {
  def apply[A <: Role](elmts: A*) : Roles[A] =
    new Roles(elmts:_*)
}
