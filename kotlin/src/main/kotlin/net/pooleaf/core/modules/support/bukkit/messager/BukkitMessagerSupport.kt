package net.pooleaf.core.modules.support.bukkit.messager

import net.md_5.bungee.api.chat.BaseComponent
import net.pooleaf.core.modules.support.common.messager.Messager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * CommandSender
 */

fun CommandSender.sendMessageWithPrefix(message: String?) {
    Messager.sendMessageWithPrefix(this, message)
}

fun CommandSender.sendMessageWithPrefix(vararg baseComponents: BaseComponent) {
    Messager.sendMessageWithPrefix(this, baseComponents)
}

fun CommandSender.sendWarning(message: String?) {
    Messager.sendWarning(this, message)
}

fun CommandSender.sendWarningWithPrefix(message: String?) {
    Messager.sendWarningWithPrefix(this, message)
}

/**
 * Player
 */

fun Player.sendMessageSafely(message: String?) {
    if (this.isOnline) Messager.sendMessage(this, message)
}

fun Player.sendMessageSafely(vararg baseComponents: BaseComponent) {
    if (this.isOnline) Messager.sendMessage(this, baseComponents)
}

fun Player.sendMessageSafelyWithPrefix(message: String?) {
    if (this.isOnline) Messager.sendMessageWithPrefix(this, message)
}

fun Player.sendMessageSafelyWithPrefix(vararg baseComponents: BaseComponent) {
    if (this.isOnline) Messager.sendMessageWithPrefix(this, baseComponents)
}

fun Player.sendWarningSafely(message: String?) {
    if (this.isOnline) Messager.sendWarning(this, message)
}

fun Player.sendWarningSafely(vararg baseComponents: BaseComponent) {
    if (this.isOnline) Messager.sendWarning(this, baseComponents)
}

fun Player.sendWarningSafelyWithPrefix(message: String?) {
    if (this.isOnline) Messager.sendWarningWithPrefix(this, message)
}

fun Player.sendWarningSafelyWithPrefix(vararg baseComponents: BaseComponent) {
    if (this.isOnline) Messager.sendWarningWithPrefix(this, baseComponents)
}