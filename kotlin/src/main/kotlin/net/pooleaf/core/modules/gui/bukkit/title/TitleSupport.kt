package net.pooleaf.core.modules.gui.bukkit.title

import net.pooleaf.core.modules.gui.bukkit.title.Title
import org.bukkit.entity.Player

fun Player.sendTitle(title: Title) {
    title.send(this)
}

fun Player.sendTitleSafely(title: Title) {
    title.sendSafely(this)
}