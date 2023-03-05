package net.pooleaf.core.modules.support.bukkit.sound

import com.cryptomorin.xseries.XSound
import org.bukkit.Location
import org.bukkit.entity.Player

fun Player.playSound(
    sound: XSound,
    volume: Float = 1.0F,
    pitch: Float = 1.0F
) {
    sound.play(this, volume, pitch)
}

fun Player.playSoundSafely(
    sound: XSound,
    volume: Float = 1.0F,
    pitch: Float = 1.0F
) {
    if (this.isOnline) sound.play(this, volume, pitch)
}

fun Player.playSound(
    sound: XSound,
    location: Location,
    volume: Float = 1.0F,
    pitch: Float = 1.0F
) {
    sound.play(this, location, volume, pitch)
}

fun Player.playSoundSafely(
    sound: XSound,
    location: Location,
    volume: Float = 1.0F,
    pitch: Float = 1.0F
) {
    if (this.isOnline) sound.play(this, location, volume, pitch)
}

fun XSound.play(
    player: Player,
    location: Location,
    volume: Float = 1.0F,
    pitch: Float = 1.0F
) {
    player.playSound(location, this.parseSound(), volume, pitch)
}