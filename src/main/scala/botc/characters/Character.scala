package org.audreyseo.lying
package botc.characters

import base.roles.Role
import botc.{Evil, Good}

abstract class Character(name: String, description: String) extends Role(name, description) {
  var roleType: BotcCharacterType = _
  def  setRoleType(r: BotcCharacterType): this.type = {
    roleType = r
    this
  }
}

abstract class PlayerCharacter(name: String, description: String) extends Character(name, description)

abstract class GoodCharacter(name: String, description: String) extends PlayerCharacter(name, description) {
  setAlignment(Good())
}

abstract class EvilCharacter(name: String, description: String) extends PlayerCharacter(name, description) {
  setAlignment(Evil())
}

