package org.audreyseo.lying
package botc.utility

trait Circular[A] extends Iterable[A] {
  def iterator: CircularIterator[A]
}


trait CircularIterator[A] extends Iterator[A] {
  def resetIterator: Iterator[A]
  var iter: Iterator[A]
  def hasNext: Boolean = true
  def next(): A = {
    while (!iter.hasNext) {
      iter = resetIterator
    }
    iter.next()
  }
}

class CircularQueue[A](s: Seq[A]) extends Circular[A] {
  def iterator = new CQueueIterator()
  class CQueueIterator extends CircularIterator[A] {
    def resetIterator: Iterator[A] = s.iterator
    var iter = resetIterator
  }
}
