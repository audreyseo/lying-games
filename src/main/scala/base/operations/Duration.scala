package org.audreyseo.lying
package base.operations

abstract class Duration {
  def past(currentTime: Timeline): Boolean
}

case class Until(t: Timeline) extends Duration {
  override def past(currentTime: Timeline) =
    t.compare(currentTime) <= 0
}

case class ForeverFrom(t: Timeline) extends Duration {
  override def past(currentTime: Timeline): Boolean =
    currentTime.compare(t) >= 0
}

case class NDaysFrom(t: Timeline, n: Int) extends Duration {
  var futureTime = t
  for (_ <- List.range(0, n)) {
    futureTime = futureTime.next24Hours
  }
  override def past(currentTime: Timeline): Boolean =
    futureTime.compare(currentTime) <= 0
}

