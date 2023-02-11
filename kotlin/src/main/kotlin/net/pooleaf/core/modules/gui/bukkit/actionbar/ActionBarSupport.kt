package net.pooleaf.core.modules.gui.bukkit.actionbar

import org.bukkit.entity.Player

fun Player.showActionBar(message: String) {
    ActionBar.show(this, message)
}

fun Player.showActionBar(message: String, seconds: Int) {
    ActionBar.show(this, message, seconds)
}

fun Player.showActionBarSafely(message: String) {
    ActionBar.showSafely(this, message)
}

fun Player.showActionBarForever(message: String) {
    ActionBar.showForever(this, message)
}

fun Player.removeActionBar() {
    ActionBar.remove(this)
}