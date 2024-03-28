package org.audreyseo.lying
package base.operations

trait HasAction {
  var action: Action
  def getAction: Action = action
  def setAction(a: Action): this.type = {
    action = a
    this
  }
  //def takeAction[A](t: Timeline, as: A*) = {
  //  if (!action.isTime(t)) {
  //    throw InappropriateTimeException(t, action.permissibleTimes)
  //  }
  //  action.takeAction[A](as :_*)
  //}
}


