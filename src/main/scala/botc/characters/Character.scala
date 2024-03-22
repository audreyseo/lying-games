package org.audreyseo.lying
package botc.characters

import botc.Evil
import botc.Good
import roles.Alignment
import roles.Role

abstract class Character(name: String, description: String) extends Role(name, description) {
  def roleType: BotcCharacterType
}

abstract class PlayerCharacter(name: String, description: String) extends Character(name, description)

abstract class GoodCharacter(name: String, description: String) extends PlayerCharacter(name, description) {
  def alignment: Alignment = Good()
}

abstract class EvilCharacter(name: String, description: String) extends PlayerCharacter(name, description) {
  def alignment: Alignment = Evil()
}

