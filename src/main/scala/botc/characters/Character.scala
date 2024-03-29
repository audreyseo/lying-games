package org.audreyseo.lying
package botc.characters

import base.roles.Role
import botc.NightOrder.AddPermissibleTimesViaRegex
import botc.{BotcPlayer, NightOrder}

import org.audreyseo.lying.botc.game.Grimoire
import org.audreyseo.lying.botc.registration.{BotcAlignment, Evil, Good}

abstract class Character(name: String, description: String) extends Role(name, description) {
  var roleType: BotcCharacterType = _
  def  setRoleType(r: BotcCharacterType): this.type = {
    roleType = r
    this
  }

  def getBotcAlignment: BotcAlignment =
    this.alignment.asInstanceOf[BotcAlignment]

}

abstract class PlayerCharacter(name: String, description: String) extends Character(name, description) with NightOrder with AddPermissibleTimesViaRegex {
  def getRoleType: PlayerCharacterType = this.roleType.asInstanceOf[PlayerCharacterType]

  def getPlayer(grim: Grimoire): Option[BotcPlayer] = {
    grim.getPlayersUnwrapped.getPlayer(this)
  }
}

abstract class GoodCharacter(name: String, description: String) extends PlayerCharacter(name, description) {
  setAlignment(Good())
}

abstract class EvilCharacter(name: String, description: String) extends PlayerCharacter(name, description) {
  setAlignment(Evil())
}

