package org.audreyseo.lying
package botc.abilities

import botc.characters.{Marionette, PlayerCharacter}

sealed abstract class Reminder(d: String) {
  var isGlobal = false
  def description: String = d
  var character: Option[PlayerCharacter] = None

  def addCharacter(p: PlayerCharacter): this.type = {
    this.character = Some(p)
    this
  }

  override def toString = {
    (if (isGlobal) "(Global) " else "") +
    (this.character match {
      case Some(p) => p.toString + " "
      case None => ""
    }) + d
  }

  def setGlobal(g: Boolean): this.type = {
    isGlobal = g
    this
  }

  def getGlobal = isGlobal
}

object Reminders {

  sealed abstract class DrunkennessReminder(d: String) extends Reminder("Drunk: " + d)


  sealed abstract class IsActuallyRoleReminder(s: String) extends Reminder("Is the " + s)
  sealed abstract class IsActuallyCharacterReminder(r: PlayerCharacter) extends IsActuallyRoleReminder(r.getName)

  sealed trait ProtectionReminder
  sealed abstract class AttackReminder(d: String) extends Reminder("Attack: " + d)

  sealed trait InfoReminder
  sealed trait AbilityReminder

  case class Attack1() extends AttackReminder("1")
  case class Attack2() extends AttackReminder("2")
  case class Attack3() extends AttackReminder("3")

  case class ThreeAttacks() extends Reminder("3 attacks")

  case class IsTheAlchemist() extends IsActuallyRoleReminder("Alchemist")
  case class IsTheApprentice() extends IsActuallyRoleReminder("Apprentice")

  case class DiedToday() extends Reminder("Died today")

  case class SurvivesExecution() extends Reminder("Survives execution")


  case class Alive() extends Reminder("Alive")
  case class Dead() extends Reminder("Dead")
  case class Executed() extends Reminder("Executed")
  case class Wrong() extends Reminder("Wrong") with InfoReminder
  case class Outsider() extends Reminder("Outsider") with InfoReminder
  case class Townsfolk() extends Reminder("Townsfolk") with InfoReminder
  case class Minion() extends Reminder("Minion") with InfoReminder
  case class RedHerring() extends Reminder("Red herring") with InfoReminder
  case class Protected() extends Reminder("Protected") with ProtectionReminder
  case class CannotDie() extends Reminder("Cannot die") with ProtectionReminder
  case class NoAbility() extends Reminder("No ability") with AbilityReminder
  case class Master() extends Reminder("Master")
  case class Drunk() extends DrunkennessReminder("Drunk")
  case class Poisoned() extends Reminder("Poisoned")
  case class Demon() extends Reminder("Demon")

  sealed abstract class VoteReminder(d: String) extends Reminder("Vote: " + d)
  case class ThreeVotes() extends VoteReminder("3 votes")
  case class NegativeVote() extends VoteReminder("Negative vote")

  case class Grandchild() extends Reminder("Grandchild")
  case class Chosen() extends Reminder("Chosen")

  case class Drunk3() extends DrunkennessReminder("3")
  case class Drunk2() extends DrunkennessReminder("2")
  case class Drunk1() extends DrunkennessReminder("1")

  case class EveryoneDrunk() extends DrunkennessReminder("Everyone")

  case class NominateGood() extends Reminder("Nominate good")
  case class NominateEvil() extends Reminder("Nominate evil")
  case class Abnormal() extends Reminder("Abnormal")
  case class DemonVoted() extends Reminder("Demon voted")
  case class DemonDidntVote() extends Reminder("Demon not voted")
  case class MinionNominated() extends Reminder("Minion not nominated")
  case class MinionNotNominated() extends Reminder("Minions not nominated")
  case class IsThePhilosopher() extends Reminder("Is the Philosopher")
  case class Correct() extends Reminder("Correct")
  case class HaircutsTonight() extends Reminder("Haircuts tonight")
  case class Twin() extends Reminder("Twin")
  case class Cursed() extends Reminder("Cursed")
  case class Mad() extends Reminder("Mad")
  case class Once() extends Reminder("Once")
  case class HasAbility() extends Reminder("HAs Ability") with AbilityReminder
  case class SoberAndHealthy() extends Reminder("Sober & Healthy")
  case class AbilityTwice() extends Reminder("Ability twice") with AbilityReminder
  case class AtASermon() extends Reminder("At a sermon")

  sealed abstract class SeenReminder(d: String) extends Reminder("Seen" + (if (d.isEmpty) "" else " " + d))
  case class SeenTownsfolk() extends SeenReminder("Townsfolk")
  case class SeenOutsider() extends SeenReminder("Outsider")
  case class SeenMinion() extends SeenReminder("Minion")
  case class SeenDemon() extends SeenReminder("Demon")
  case class SeenTraveler() extends SeenReminder("Traveler")
  case class Seen() extends SeenReminder("")
  case class QuestionMark() extends Reminder("?")

  case class GuessUsed() extends Reminder("Guess used")
  case class CannotNominate() extends Reminder("Cannot nominate")
  case class Knows() extends Reminder("Knows")
  case class Fear() extends Reminder("Fear")
  case class Claimed() extends Reminder("Claimed")
  case class TurnsEvil() extends Reminder("Turns evil")
  case class IsTheMarionette() extends IsActuallyCharacterReminder(Marionette())
  case class IsTheDemon() extends Reminder("Is the Demon")

  case class Known() extends Reminder("Known")

  sealed abstract class NumberReminder(num: Int) extends Reminder(num.toString)
  case class One() extends NumberReminder(1)
  case class Two() extends NumberReminder(2)
  case class Three() extends NumberReminder(3)

  sealed abstract class ChoseReminder(d: String) extends Reminder("Chose " + d)
  case class ChoseDeath() extends ChoseReminder("death")
  case class ChoseLife() extends ChoseReminder("life")
  case class AboutToDie() extends Reminder("About to die")

  sealed abstract class DayReminder(day: Int) extends Reminder("Day " + day.toString)
  case class Day1() extends DayReminder(1)
  case class Day2() extends DayReminder(2)
  case class Day3() extends DayReminder(3)
  case class Day4() extends DayReminder(4)
  case class Day5() extends DayReminder(5)

  case class GoodPlayerExecuted() extends Reminder("Good player executed")

  case class EvilWakes() extends Reminder("Evil wakes")

  case class SomethingBad() extends Reminder("Something Bad")
  case class Protect() extends Reminder("Protect")
  case class Used() extends Reminder("Used")
  case class FinalNightNoAttack() extends Reminder("Final Night: No Attack")
  case class Visitor() extends Reminder("Visitor")
  case class FalseInfo() extends Reminder("False Info")
  case object NoExtraEvil extends Reminder("No extra evil")
  case object Safe extends Reminder("Safe")

}


trait HasReminders {
  var reminders: Set[Reminder] = Set.empty

  def setGlobals(f: Reminder => Boolean) : this.type = {
    reminders = for (r <- reminders) yield {
      r.setGlobal(f(r))
    }
    this
  }

  def addReminder(r: Reminder): this.type = {
    reminders = reminders + r
    this match {
      case character: PlayerCharacter =>
        addCharacter(character)
      case _ => this
    }
  }

  def addReminders(rs: Reminder*): this.type = {
    for (r <- rs) {
      addReminder(r)
    }
    this
  }

  def addCharacter(c: PlayerCharacter): this.type = {
    reminders = for (r <- reminders) yield {
      r.addCharacter(c)
    }
    this
  }
}

object HasReminders {
  import botc.abilities.Reminders._
  trait HasDead extends HasReminders {
    addReminder(Dead())
  }

  trait HasNoAbility extends HasReminders {
    addReminder(NoAbility())
  }

  trait HasAbility extends HasReminders {
    addReminder(HasAbility())
  }

  trait HasDrunk extends HasReminders {
    addReminder(Drunk())
  }

  trait HasPoisoned extends HasReminders {
    addReminder(Poisoned())
  }
  trait HasProtected extends HasReminders {
    addReminder(Protected())
  }
  trait HasAlive extends HasReminders {
    addReminder(Alive())
  }

  trait HasDiedToday extends HasReminders {
    addReminder(DiedToday())
  }

  trait HasGuessUsed extends HasReminders {
    addReminder(GuessUsed())
  }

  trait HasTurnsEvil extends HasReminders {
    addReminder(TurnsEvil())
  }

  trait HasWrong extends HasReminders {
    addReminder(Wrong())
  }

  trait HasMad extends HasReminders {
    addReminder(Mad())
  }

  trait HasUsed extends HasReminders {
    addReminder(Used())
  }
  trait HasSomethingBad extends HasReminders {
    addReminder(SomethingBad())
  }
}

