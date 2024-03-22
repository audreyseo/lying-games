package org.audreyseo.lying
package roles

abstract class Role(name: String, description: String) extends HasAlignment {
  def getName = name
  def getDescription = description
}
