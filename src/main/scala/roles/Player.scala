package org.audreyseo.lying
package roles

abstract class Player(name: String, r: Role) extends HasAlignment {
  var role = r
  def getName = name
  def getRole = role

  var self_alignment: Option[Alignment]
  def alignment: Alignment = self_alignment match {
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
  override def getAlignment: Alignment = alignment

  var status: Status = Alive()
  def getStatus: Status = status
  def makeDead(): this.type = {
    this.status = Dead()
    this
  }

  def makeAlive(): this.type = {
    this.status = Alive()
    this
  }

  override def equals(arg0: Any): Boolean = {
    arg0 match {
      case player: this.type =>
        player.getName == this.getName
      case _ => false
    }
  }

}
