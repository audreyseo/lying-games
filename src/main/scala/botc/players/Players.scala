package org.audreyseo.lying
package botc.players

import botc.BotcPlayer

import org.audreyseo.lying.botc.characters.PlayerCharacter
import org.audreyseo.lying.botc.utility.PlayerIterator

import scala.collection.immutable.HashMap

class Players(players: Iterable[BotcPlayer]) extends Iterable[BotcPlayer] {
  private val playerNamesToCharacters: HashMap[String, PlayerCharacter] =
    HashMap(players.toList.map(player => (player.getName, player.getRole.asInstanceOf[PlayerCharacter])) :_*)
  private val playerNames: Set[String] =
    players.foldLeft(Set.empty[String])(
      (acc, player) =>
      acc + (player.getName)
    )

  private val charactersToPlayers: HashMap[PlayerCharacter, BotcPlayer] =
    HashMap(players.toList.map(player => (player.getRole.asInstanceOf[PlayerCharacter], player)) :_*)
  private val characterNamesToPlayers: HashMap[String, BotcPlayer] =
    HashMap(players.toList.map(player =>
                                 (player.getPlayerCharacter.getName, player)) :_*)

  private val playerNamesToPlayers: HashMap[String, BotcPlayer] =
    HashMap(players.toList.map(player => (player.getName, player)):_*)

  private val characterNamesToPlayerCharacters: HashMap[String, PlayerCharacter] =
    HashMap(players.toList.map(player => (player.getPlayerCharacter.getName, player.getPlayerCharacter)) :_*)

  def iterator: Iterator[BotcPlayer] = players.iterator
  def circleIterator: PlayerIterator = new PlayerIterator(players.toList.head)

  def getCharacter(characterName: String) : Option[PlayerCharacter] =
    characterNamesToPlayerCharacters.get(characterName)

  def containsCharacter(characterName: String): Boolean =
    characterNamesToPlayerCharacters.contains(characterName)

  def getPlayersCharacter(playerName: String): Option[PlayerCharacter] =
    playerNamesToCharacters.get(playerName)

  def getPlayer(playerName: String): Option[BotcPlayer] =
    playerNamesToPlayers.get(playerName)

  def getPlayer(character: PlayerCharacter): Option[BotcPlayer] =
    charactersToPlayers.get(character)

  def getPlayerFromCharacterName(characterName: String): Option[BotcPlayer] =
    characterNamesToPlayers.get(characterName)

  def containsPlayer(playerName: String) =
    playerNames.contains(playerName)



}
