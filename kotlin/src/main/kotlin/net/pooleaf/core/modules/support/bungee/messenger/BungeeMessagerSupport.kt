package net.pooleaf.core.modules.support.bungee.messenger

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.pooleaf.core.modules.support.common.messager.Messager
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

fun ProxiedPlayer.sendWarningSafely(message: String) {
    if (this.isConnected) Messager.sendWarning(this, message)
}

fun ProxiedPlayer.sendWarningSafely(vararg baseComponents: BaseComponent) {
    if (this.isConnected) Messager.sendWarning(this, baseComponents)
}

fun ProxiedPlayer.sendWarningSafelyWithPrefix(message: String) {
    if (this.isConnected) Messager.sendWarningWithPrefix(this, message)
}

fun ProxiedPlayer.sendWarningSafelyWithPrefix(vararg baseComponents: BaseComponent) {
    if (this.isConnected) Messager.sendWarningWithPrefix(this, baseComponents)
}