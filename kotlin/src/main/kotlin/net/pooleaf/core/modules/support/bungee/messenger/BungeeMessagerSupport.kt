package net.pooleaf.core.modules.support.bungee.messenger

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.pooleaf.core.modules.support.common.messager.Messager

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

fun ProxiedPlayer.sendMessageSafely(message: String) {
    if (this.isConnected) Messager.sendMessage(this, message)
}

fun ProxiedPlayer.sendMessageSafely(vararg baseComponents: BaseComponent) {
    if (this.isConnected) Messager.sendMessage(this, baseComponents)
}

fun ProxiedPlayer.sendMessageSafelyWithPrefix(message: String) {
    if (this.isConnected) Messager.sendMessageWithPrefix(this, message)
}

fun ProxiedPlayer.sendMessageSafelyWithPrefix(vararg baseComponents: BaseComponent) {
    if (this.isConnected) Messager.sendMessageWithPrefix(this, baseComponents)
}