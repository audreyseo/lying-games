package org.audreyseo.lying
package botc

import botc.characters.PlayerCharacter
import botc.utility.CharacterDoesNotExistError
import botc.utility.CircularQueue
import botc.utility.NotEnoughCharactersForPlayersError
import botc.utility.RanOutOfCharactersError
import botc.utility.RandomIterator

class Grimoire(s: Script, p: String*) {
  private var playersNames = p
  private var players: CircularQueue[BotcPlayer] = _
  private def script = s
  private var assignableCharacters: Set[PlayerCharacter] = Set.empty
  private var assignedCharacters: Set[PlayerCharacter] = Set.empty
  def getPlayers = players
  def getScript = script

  def addCharacter(name: String): this.type = {
    script.getRole(name) match {
      case Some(r) => assignableCharacters = assignableCharacters + r
      case None => throw CharacterDoesNotExistError(name, s"Could not find ${name} in script ${script.scriptName}")
    }
    this
  }

  def pickCharacters(): this.type = {
    if (assignableCharacters.size < playersNames.size) {
      throw NotEnoughCharactersForPlayersError(assignableCharacters.size, playersNames.size)
    }
    val rand = new RandomIterator(assignableCharacters.toList)

    val playersList = for (p <- playersNames)
      yield {
        if (!rand.hasNext) {
          throw RanOutOfCharactersError(s"Ran out of assignable characters from list ${assignableCharacters} for players ${playersNames}")
        }
        new BPlayer(p, rand.next())
      }

    players = new CircularQueue(playersList)
    this
  }

}
