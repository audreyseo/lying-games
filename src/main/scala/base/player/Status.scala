package org.audreyseo.lying
package base.player

sealed trait Status {
}

case object Alive extends Status

case object Dead extends Status

