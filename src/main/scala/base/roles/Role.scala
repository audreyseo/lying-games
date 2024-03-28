package org.audreyseo.lying
package base.roles

import base.roles.alignments.HasAlignment

abstract class Role(name: String, d: String) extends HasAlignment {
  def description = d
  def getName = name
  def getDescription = description
}
