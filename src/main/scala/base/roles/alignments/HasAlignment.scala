package org.audreyseo.lying
package base.roles.alignments

trait HasAlignment {
  var alignment: Alignment = _
  def getAlignment: Alignment = alignment
  def setAlignment(a: Alignment): this.type = {
    alignment = a
    this
  }
}
