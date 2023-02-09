package net.pooleaf.core.modules.support.bukkit.messager

import net.md_5.bungee.api.chat.BaseComponent
import net.pooleaf.core.modules.support.common.messager.Messager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * CommandSender
 */

fun CommandSender.sendMessageWithPrefix(message: String) {
    Messager.sendMessageWithPrefix(this, message)
}

fun CommandSender.sendMessageWithPrefix(vararg baseComponents: BaseComponent) {
    Messager.sendMessageWithPrefix(this, baseComponents)
}

/**
 * Player
 */

fun Player.sendMessageSafely(message: String) {
    if (this.isOnline) Messager.sendMessage(this, message)
}

fun Player.sendMessageSafely(vararg baseComponents: BaseComponent) {
    if (this.isOnline) Messager.sendMessage(this, baseComponents)
}

fun Player.sendMessageSafelyWithPrefix(message: String) {
    if (this.isOnline) Messager.sendMessageWithPrefix(this, message)
}

fun Player.sendMessageSafelyWithPrefix(vararg baseComponents: BaseComponent) {
    if (this.isOnline) Messager.sendMessageWithPrefix(this, baseComponents)
}