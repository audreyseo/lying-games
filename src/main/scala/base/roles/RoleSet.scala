package org.audreyseo.lying
package base.roles

trait RoleSet[A <: Role] extends Set[A] {
  def getRole(s: String) = {
    this.find((a: A) => a.getName == s)
  }

  def hasRole(s: String) =
    this.exists((a: A) => a.getName == s)
}
