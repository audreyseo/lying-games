package org.audreyseo.lying
package roles

sealed trait Status {
}

case class Alive() extends Status

case class Dead() extends Status

