package org.audreyseo.lying
package base.operations

//import scala.reflect.runtime.universe._
trait Action {
  def permissibleTimes: Time
  def isTime(t: Timeline): Boolean = t.isTime(permissibleTimes)
  //def takeAction[A: TypeTag](targets: A*): this.type
}

case class InappropriateTimeException(currentTime: Timeline, permissibleTimes: Time) extends Exception
