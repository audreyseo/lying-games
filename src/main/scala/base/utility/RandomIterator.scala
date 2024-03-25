package org.audreyseo.lying
package base.utility

import scala.util.Random

class RandomIterator[A](l: List[A]) extends Iterator[A] {
  val internalList: List[A] = Random.shuffle(l)
  val iter = internalList.iterator
  def hasNext = iter.hasNext
  def next() = iter.next()
}
