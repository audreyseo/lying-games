package org.audreyseo.lying
package base

trait Configuration {
  var generateAllGameStates : Boolean = true

  def setGameStateGeneration(shouldGenerateGameStates: Boolean) : this.type = {
    this.generateAllGameStates = shouldGenerateGameStates
    this
  }

  def getGameStateGeneration: Boolean =
    generateAllGameStates
}
