package org.audreyseo.lying
package base.player

import scala.reflect.runtime.universe._
import base.utility.TypeUtils._

import org.audreyseo.lying.base.utility.TypeUtils

trait Metadata[A] {
  var metadata: List[A] = List.empty
  var typesAndMetadata: List[(Type, A)] = List.empty

  /* Some reflection shenanigans you have to do in order to
   * make the type bounds work out, since the types are erased
   * at runtime, and you can't put context bounds on the trait's
   * type parameter.
   */
  def addMetadata[B <: A : TypeTag](a: B): this.type = {
    metadata = metadata ::: List(a)
    // I couldn't figure out how to postpend here, but then again, the order doesn't exactly matter here either
    typesAndMetadata = List((getType(a), a)) ::: typesAndMetadata
    this
  }

  def removeMetadata[B <: A: TypeTag](a: B): this.type = {
    metadata = metadata.filterNot(_.equals(a))
    typesAndMetadata = typesAndMetadata.filterNot{
      case (t, b) =>
        t =:= typeOf[B] && b.equals(a)
    }
    this
  }

  def containsMetadata[B <: A : TypeTag](a: A): Boolean = {
    metadata.contains(a)
  }

  def filterMetadata(f: A => Boolean): List[A] = {
    metadata.filter(f)
  }

  private def filterTypesAndMetadata(f: ((Type, A)) => Boolean): List[A] = {
    typesAndMetadata.filter(f).map {
      case (_, a: A) => a
    }
  }

  def getMetadataOfType[T: TypeTag]: List[T] = {
    filterTypesAndMetadata {
      case (t: Type, _) => t weak_<:< typeOf[T]
    }.map(_.asInstanceOf[T])
  }
}
