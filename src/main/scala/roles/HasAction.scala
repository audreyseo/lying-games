package org.audreyseo.lying
package roles

trait HasAction {
  def action: Action
  def getAction: Action = action
}


