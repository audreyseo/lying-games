package org.audreyseo.lying
package base

object Configuration {
  var generateAllGameStates : Boolean = true

  def setGameStateGeneration(shouldGenerateGameStates: Boolean) : this.type = {
    this.generateAllGameStates = shouldGenerateGameStates
    this
  }

  def getGameStateGeneration: Boolean =
    generateAllGameStates
}
