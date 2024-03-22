package org.audreyseo.lying
package botc.utility

case class InvalidNumberOfPlayersError(invalid_number: Int, message: String) extends Exception

case class CharacterDoesNotExistError(invalidName: String, message: String) extends Exception

case class NotEnoughCharactersForPlayersError(numCharacters: Int, numPlayers: Int) extends Exception

case class RanOutOfCharactersError(message: String) extends Exception
