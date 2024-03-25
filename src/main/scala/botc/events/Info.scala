package org.audreyseo.lying
package botc.events

sealed trait Info[I] {
  def getInfo: I
}
