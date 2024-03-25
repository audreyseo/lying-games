package org.audreyseo.lying
package base.player

import base.roles.Role
import base.roles.alignments.{Alignment, HasAlignment}

abstract class Player(name: String, r: Role) extends HasAlignment {
  private var role = r
  def getName = name
  def getRole = role

  private var self_alignment: Option[Alignment] = None
  setAlignment(role.alignment)
  override def getAlignment: Alignment = self_alignment match {
    case Some(a) => a
    case None => role.getAlignment
  }

  def changeAlignment(a: Alignment): this.type = {
    this.self_alignment = Some(a)
    this
  }

  def changeRole(r: Role): this.type = {
    this.role = r
    this
  }


  var status: Status = Alive
  def getStatus: Status = status
  def makeDead(): this.type = {
    this.status = Dead
    this
  }

  def isDead: Boolean = status == Dead

  def makeAlive(): this.type = {
    this.status = Alive
    this
  }

  override def equals(arg0: Any): Boolean = {
    arg0 match {
      case player: this.type =>
        player.getName == this.getName
      case _ => false
    }
  }

  def takeAction[A](targets: A*): this.type
}
