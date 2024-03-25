package org.audreyseo.lying
package botc.game

import botc.{AddCharacter, AllEvilAre, AllMinionsAre, AllOfModifier, AnyGood, BotcModifier, ImplicationModifier, ModifyMinions, ModifyOutsiders, NoEvil, NoMinions, NoOpModifier, SwitchesAllAlignments}
import botc.characters.{DemonType, MinionType, OutsiderType, PlayerCharacter, TownsfolkType}

import org.audreyseo.lying.base.roles.HasModifier


object Setup {
  def calculatePairsThatAddTo(n: Int) = {
    List
      .range(-n, n + 1)
      .map(k => (k, n - k))
  }

  private def nonnegativeTuple(tup: (Int, Int, Int, Int)): Boolean = {
    tup match {
      case (t, o, m, d) => t >= 0 && o >= 0 && m >= 0 && d >= 0
    }
  }



  def calculatePossibilities(tuples: Set[(Int, Int, Int, Int)], modifier: BotcModifier): Set[(Int, Int, Int, Int)] = {
    val result: Set[(Int, Int, Int, Int)] = modifier match {
      case NoOpModifier() => tuples
      case ModifyOutsiders(delta) =>
        tuples
          .map {
            case (t, o, m, d) => (t - delta, o + delta, m, d)
          }
      case ModifyMinions(delta) =>
        tuples
          .map {
            case (t, o, m, d) => (t, o, m + delta, d - delta)
          }
      case NoMinions() =>
        tuples
          .flatMap(tup => tup match {
            case (t, o, m, d) => calculatePairsThatAddTo(m)
              .map {
                case (m1, m2) => (t + m1, o + m2, 0, d)
              }.toSet
          }).filter(nonnegativeTuple)
      case NoEvil() =>
        tuples.flatMap{
          case (t, o, m, d) =>
            calculatePairsThatAddTo(m + d).map{
              case (m1, m2) => (t + m1, o + m2, 0, 0)
            }.toSet
        }.filter(nonnegativeTuple)
      case AnyGood =>
        tuples.map{
          case (t, o, m, d) => (t + o, m, d)
        }.flatMap{
          case (g, m, d) =>
            calculatePairsThatAddTo(g).filter{
              case (a, b) => a >= 0 && b >= 0
            }.map{
              case (a, b) =>
                (a, b, m, d)
            }
        }
      case AddCharacter(c) =>
        tuples.map{
          case (t, o, m, d) =>
            c.roleType match {
              case TownsfolkType() => (t + 1, o - 1, m, d)
              case OutsiderType() => (t - 1, o + 1, m, d)
              case MinionType() => (t, o, m + 1, d - 1)
              case DemonType() => (t, o, m - 1, d + 1)
            }
        }.filter(nonnegativeTuple)

      case ImplicationModifier(m1, m2) =>
        calculatePossibilities(calculatePossibilities(tuples, m1), m2)
      case AllOfModifier(mods @_*) =>
        if (mods
          .isEmpty) {
          Set.empty
        } else if (mods.size == 1) {
          calculatePossibilities(tuples, mods.head)
        } else {
          mods.tail.foldLeft(calculatePossibilities(tuples, mods.head))(
            (acc: Set[(Int, Int, Int, Int)], mod: BotcModifier) =>
              acc.intersect(calculatePossibilities(tuples, mod))
            )
        }
      case AllMinionsAre(c) =>
        tuples.map{
          case (t, o, m, d) =>
            c.roleType match {
              case TownsfolkType() => (t + m, o, 0, d)
              case OutsiderType() => (t, o + m, 0, d)
              case DemonType() => (t, o, 0, d + m)
            }
        }
      case AllEvilAre(c) =>
        tuples.map {
          case (t,o,m, d) =>
            c.roleType match {
              case TownsfolkType() => (t + m + d, o, 0, 0)
              case OutsiderType() => (t, o + m + d, 0, 0)
              case MinionType() => (t, o, m + d, 0)
              case DemonType() => (t,o, 0, m + d)
            }
        }
      case SwitchesAllAlignments =>
        tuples.map{
          case (t, o, m, d) => (m, d, t, o)
        }
      case _ => tuples
    }
    result
  }

  def getAddedCharacters(c: botc.characters.PlayerCharacter): Set[botc.characters.PlayerCharacter] = {
    c match {
      case hm: HasModifier =>
        getAddedCharacters(List(hm.mod.asInstanceOf[BotcModifier])).filter(c => c.isInstanceOf[botc.characters.PlayerCharacter]).map(c => c.asInstanceOf[botc.characters.PlayerCharacter])
      case _ => Set.empty[botc.characters.PlayerCharacter]
    }
  }

  def getAddedCharacters(mods: Iterable[BotcModifier]): Set[botc.characters.Character] = {
    mods.foldLeft(Set.empty[botc.characters.Character])(
      (acc, mod) =>
      mod match {
        case AddCharacter(role) => acc + role
        case a: AllOfModifier => acc ++ (getAddedCharacters(a.getMods))
        case ImplicationModifier(m1, m2) =>
          acc ++ getAddedCharacters(List(m1, m2))
        case AllMinionsAre(c) => acc + c
        case AllEvilAre(c) => acc + c
        case _ => acc
      }
    )
  }
}
