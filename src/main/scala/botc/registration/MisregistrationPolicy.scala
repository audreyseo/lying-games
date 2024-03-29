package org.audreyseo.lying
package botc.registration

import org.audreyseo.lying.botc.game.Grimoire

import scala.math.random

sealed trait MisregistrationPolicy {
  def shouldMisregister: Boolean
}

object MisregistrationPolicy {
  case class Probabilistic(p: Double) extends MisregistrationPolicy {
    assert(p >= 0, s"Probability must be at least 0, but found $p")
    assert(p <= 1, s"Probability must be less than or equal to 1, but found $p")
    def shouldMisregister: Boolean = {
      random() <= p
    }
  }

  case object Always extends MisregistrationPolicy {
    def shouldMisregister: Boolean = true
  }

  case object Never extends MisregistrationPolicy {
    override def shouldMisregister = false
  }

  case class Custom(p: () => Boolean) extends MisregistrationPolicy {
    def shouldMisregister: Boolean = {
      p()
    }
  }

  sealed abstract class ContextDependent(grim: Grimoire) extends MisregistrationPolicy {
    def getGrimoire: Grimoire = grim
  }

  case class DependsOn(grim: Grimoire, p: Grimoire => Boolean) extends ContextDependent(grim) {
    override def shouldMisregister =
      p(grim)
  }
}
