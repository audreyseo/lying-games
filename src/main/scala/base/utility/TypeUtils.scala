package org.audreyseo.lying
package base.utility

import scala.reflect.runtime.universe._

object TypeUtils {
  def getType[T: TypeTag](obj: T) = typeOf[T]
  def isSubtype[T1: TypeTag, T2: TypeTag]: Boolean =
    typeOf[T1] weak_<:< typeOf[T2]

  def typeEquivalence[T1: TypeTag, T2: TypeTag]: Boolean =
    typeOf[T1] =:= typeOf[T2]
}
