package org.audreyseo.lying
package base.player

import scala.reflect.runtime.universe._
import base.utility.TypeUtils._

trait Metadata[A] {
  var metadata: List[A] = List.empty
  def addMetadata(a: A): this.type = {
    metadata = metadata ::: List(a)
    this
  }

  def containsMetadata(a: A): Boolean = {
    metadata.contains(a)
  }

  def filterMetadata(f: A => Boolean): List[A] = {
    metadata.filter(f)
  }

  def getMetadataOfType[T : TypeTag]: List[T] = {
    filterMetadata(a => isSubtype[a.type, T])
            .map(_.asInstanceOf[T])
  }
}
