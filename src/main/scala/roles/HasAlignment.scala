package org.audreyseo.lying
package roles

trait HasAlignment {
  def alignment: Alignment
  def getAlignment: Alignment = alignment
}
