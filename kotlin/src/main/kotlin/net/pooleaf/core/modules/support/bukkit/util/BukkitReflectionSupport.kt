package net.pooleaf.core.modules.support.bukkit.util

import org.bukkit.block.Block

fun Block.getBreakSound(): String {
    return BukkitReflectionUtil.getBlockBreakSound(this)
}

fun Block.getPlaceSound(): String {
    return BukkitReflectionUtil.getBlockPlaceSound(this)
}

fun Block.getStepSound(): String {
    return BukkitReflectionUtil.getBlockStepSound(this)
}

fun Block.getSoundVolume(): Float {
    return BukkitReflectionUtil.getBlockSoundVolume(this)
}

fun Block.getSoundPitch(): Float {
    return BukkitReflectionUtil.getBlockSoundPitch(this)
}