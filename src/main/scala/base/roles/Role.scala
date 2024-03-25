package org.audreyseo.lying
package base.roles

import base.roles.alignments.HasAlignment

abstract class Role(name: String, description: String) extends HasAlignment {
  def getName = name
  def getDescription = description
}
