package org.audreyseo.lying
package botc.utility

import scala.util.Random

class RandomIterator[A](l: List[A]) extends Iterator[A] {
  def internalList: List[A] = Random.shuffle(l)
  def iter = internalList.iterator
  def hasNext = iter.hasNext
  def next() = iter.next()
}
