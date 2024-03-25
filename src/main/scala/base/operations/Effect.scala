package org.audreyseo.lying
package base.operations

trait Effect[A] {
  def getEffect: A
  def duration: Duration
}
