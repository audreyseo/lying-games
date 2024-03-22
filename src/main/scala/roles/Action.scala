package org.audreyseo.lying
package roles


abstract class Action {
  def time: Time
  def precedence: Precedence
}

