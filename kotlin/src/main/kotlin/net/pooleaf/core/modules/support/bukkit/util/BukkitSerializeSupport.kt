package net.pooleaf.core.modules.support.bukkit.util

import org.bukkit.configuration.serialization.ConfigurationSerializable

fun ConfigurationSerializable.serializeToMap(): LinkedHashMap<String, Any?> {
    return BukkitSerializeUtil.serializeToMap(this)
}

fun Map<*, *>.deserializeFromMap(): Any? {
    return BukkitSerializeUtil.deserializeFromMap(this as Map<String, Any>?)
}

fun ConfigurationSerializable.serializeToJson(): String {
    return BukkitSerializeUtil.serializeToJson(this)
}

fun String.deserializeFromJson(): Any? {
    return BukkitSerializeUtil.deserializeFromJson(this)
}