package org.audreyseo.lying
package botc.game

import base.GameState
import base.player.Player
import base.roles.{HasModifier, RoleType}
import base.utility.{RandomIterator, TypeUtils}
import botc._
import botc.characters._
import botc.scripts.{RaceToTheBottom, Script, TroubleBrewing}
import botc.utility.{CharacterDoesNotExistError, CircularQueue, NotEnoughCharactersForPlayersError, RanOutOfCharactersError}

import org.audreyseo.lying.base

import scala.reflect.runtime.universe._

class Grimoire(s: Script, p: String*) extends HasAssignableCharacters with GameState {
  private var playersNames = p
  override var players: Option[Iterable[Player]] = None
  
  private def playersAsNonCircularIterable: Iterable[BotcPlayer] =
    players match {
      case Some(p) =>
        p match {
          case value: CircularQueue[BotcPlayer] =>
            value
              .getSeq
          case _ => throw new Exception("Grimoire.players initialized to something other than a CircularQueue[BotcPlayer]")
        }
      case None =>
        throw new Exception("Tried to get players before Grimoire.players has been initialized")
    }


  private var phase: Phase.Phase = Phase.NotStarted

  private var nightNum = 0
  private var dayNum = 0

  def startNight(): this.type = {
    assert(phase == Phase.NotStarted || phase.isInstanceOf[Phase.Day])
    nightNum += 1
    phase = Phase.NightPhase(nightNum)
    this
  }

  def isFirstNight: Boolean = phase match {
    case Phase.NightPhase(1) => true
    case _ => false
  }

  def startedPlaying: Boolean = phase != Phase.NotStarted
  def isNight: Boolean = phase.isNight

  def nextPhase(): this.type = {
    if (!startedPlaying) {
      startNight()
    } else {
      phase = phase match {
        case Phase.NightPhase(n) =>
          nightNum = n
          dayNum += 1
          Phase.Talking(dayNum)
        case Phase.Talking(n) =>
          Phase.Nomination(n)
        case Phase.Nomination(_) =>
          nightNum += 1
          Phase.NightPhase(nightNum)
      }
    }
    this
  }

  private def script = s

  private var otherCharacters: Set[botc.characters.Character] = Set
    .empty
  private var assignedCharacters: Set[PlayerCharacter] = Set
    .empty

  if (s.demonRoles.size == 1) {
    addCharacter(s.demonRoles.head.getName)
  }

  def isTeensyville: Boolean = gameSize <= 6

  def nightIterator: Iterator[BotcPlayer] = {
    if (!isNight) {
      throw Grimoire.PlayException("Cannot call nightIterator outside of night phase")
    }
    val playersInner = players.asInstanceOf[CircularQueue[BotcPlayer]].iterator.iter
    val playersList = playersInner.toList
    val filtered = playersList.filter(_.hasNightAction(nightNum)).sortBy(_.getPrecedence(nightNum))
    if (isFirstNight && !isTeensyville) {
      // Add minion and demon info for non-teensyville games
      (playersList.filter(_.isMinion)
                 .sortBy(_.getName) ++
        playersList.filter(_.isDemon).sortBy(_.getName) ++
        filtered).iterator
    } else {
      filtered.iterator
    }

  }

  def getPlayers = players

  def getScript = script

  def addCharacter(name: String): this.type = {
    script
      .getRole(name) match {
      case Some(r) => r match {
        case p: PlayerCharacter => assignableCharacters = (assignableCharacters ++ Setup.getAddedCharacters(p)) + p
        case c: botc.characters.Character =>
          otherCharacters = otherCharacters + c
      }
      case None => throw CharacterDoesNotExistError(name,
                                                    s"Could not find $name in script ${
                                                      script
                                                        .scriptName
                                                    }")
    }
    this
  }


  def getNumbers: (Int, Int, Int, Int) = {
    (getTownsfolk.size, getOutsiders.size, getMinions.size, getDemons.size)
  }

  override def gameSize = playersNames
    .size

  def calculateActual: Set[(Int, Int, Int, Int)] = {

    val size = gameSize
    val (town, outsiders, minions, demons) = botc.characters.getNumbers(size)
    val modifying = filterCharacter[HasModifier]
      .map(_.mod)
      .filter(_.isInstanceOf[BotcModifier])
      .map(_.asInstanceOf[BotcModifier])
    val init = Set((town, outsiders, minions, demons))
    modifying
      .foldLeft(init)(
        (acc, mod) =>
          Setup.calculatePossibilities(acc, mod)
        )
  }

  def findMissing: Set[(Int, Int, Int, Int)] = {
    if (assignableCharacters
      .size == playersNames
      .size) {
      val actual = calculateActual
      val (town, outsiders, minions, demons) = getNumbers
      actual.map{
        case (t, o, m, d) =>
          (t - town, o - outsiders, m - minions, d - demons)
      }
    } else {
      Set.empty
    }

  }

  def checkCharacters: Boolean = {
    if (assignableCharacters
      .size == playersNames
      .size) {
      val actual = calculateActual
      val (town, outsiders, minions, demons) = getNumbers
      actual.contains((town, outsiders, minions, demons))
    } else {
      false
    }
  }

  def pickCharacters(): this.type = {
    if (assignableCharacters
      .size < playersNames
      .size) {
      throw NotEnoughCharactersForPlayersError(assignableCharacters
                                                 .size,
                                               playersNames
                                                 .size)
    }
    val rand = new RandomIterator(assignableCharacters
                                    .toList)

    val playersList = for (p <- playersNames)
      yield {
        if (!rand
          .hasNext) {
          throw RanOutOfCharactersError(s"Ran out of assignable characters from list $assignableCharacters for players $playersNames")
        }
        new BPlayer(p,
                    rand
                      .next())
      }


    players = Some(new CircularQueue(playersList))
    val iter = players.get.iterator
    val first = iter.next()
    var old = first.asInstanceOf[BotcPlayer]
    var nxt = iter.next().asInstanceOf[BotcPlayer]
    val oldNxt = nxt
    old.assignRight(nxt)
    nxt.assignLeft(old)
    old = nxt
    nxt = iter.next().asInstanceOf[BotcPlayer]
    old.assignRight(nxt)
    nxt.assignLeft(old)
    old = nxt
    nxt = iter.next().asInstanceOf[BotcPlayer]
    while (iter.hasNext && !old.equals(oldNxt)) {
      old.assignRight(nxt)
      nxt.assignLeft(old)
      old = nxt
      nxt = iter.next().asInstanceOf[BotcPlayer]
    }
    this
  }


  override def won[A <: RoleType : TypeTag]: Boolean = {
    if (TypeUtils.isSubtype[A, EvilCharacterType]) {
      EvilCharacterType.won(playersAsNonCircularIterable)
    } else if (base
      .utility
      .TypeUtils.isSubtype[A, GoodCharacterType]) {
      GoodCharacterType.won(playersAsNonCircularIterable)
    } else {
      false
    }
    //if (classTag[A].runtimeClass.isInstanceOf[]
  }

  override def lost[A <: RoleType: TypeTag]: Boolean =
    if (base
      .utility
      .TypeUtils.isSubtype[A, EvilCharacterType] || base
      .utility
      .TypeUtils.isSubtype[A, GoodCharacterType]) {
      won[A]
    } else {
      false
    }
}

object Grimoire {

  case class PlayException(message: String) extends Exception()

  import Setup._

  def testA[A <: RoleType : TypeTag] : Boolean = {
    base
      .utility
      .TypeUtils.isSubtype[A, EvilCharacterType]
  }

  def main(args: Array[String]): Unit = {
    println(calculatePossibilities(Set((3, 1, 1, 1)), ModifyOutsiders(2)))
    println(calculatePossibilities(Set((3, 1, 1, 1)), NoMinions()))
    println(calculatePossibilities(Set((3, 1, 1, 1)), ImplicationModifier(NoEvil(), AnyGood)))
    println(calculatePossibilities(Set((3, 1, 1, 1)), AddCharacter(Damsel())))
    println(calculatePossibilities(Set((3, 1, 1, 1)), Legion.mod))
    println(calculatePossibilities(Set((4, 1, 1, 1)), Legion.mod))
    val grim = new Grimoire(RaceToTheBottom, "Audrey", "Claire", "Alexandra", "Siddharth", "Logan")
    grim.addCharacter("Scarlet Woman")
    grim.addCharacter("Clockmaker")
    grim.addCharacter("Empath")
    grim.addCharacter("Dreamer")
    println(grim.filterCharacter[Empath])
    println(ScarletWoman().isInstanceOf[Empath])
    println(grim.getDemons.filter(d => d.isInstanceOf[HasModifier]))
    println(grim.calculateActual)
    testTroubleBrewing()
    println(testA[EvilCharacterType])
    println(testA[GoodCharacterType])
  }

  def testTroubleBrewing() = {
    val grim = new Grimoire(TroubleBrewing, "Audrey", "Claire", "Alexandra" , "Siddharth", "Logan", "Aman", "Christy", "Brandon")
    grim.addCharacter("Baron")
    grim.addCharacter("Empath")
    grim.addCharacter("Ravenkeeper")
    grim.addCharacter("Drunk")
    grim.addCharacter("Saint")
    grim.addCharacter("Fortune Teller")
    grim.addCharacter("Washerwoman")
    println(grim.assignableCharacters)
    println(grim.calculateActual)
    println(grim.checkCharacters)
    println(grim.findMissing)
    grim.pickCharacters()
    println(grim.players)
    grim.startNight()
    for (p <- grim.nightIterator) {
      println(p)
    }
  }
}
