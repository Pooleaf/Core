package net.pooleaf.core.modules.coroutine.common

import kotlinx.coroutines.CoroutineScope
import net.pooleaf.core.modules.coroutine.bukkit.BukkitAsyncDispatcher
import net.pooleaf.core.modules.coroutine.bungee.BungeeAsyncDispatcher
import net.pooleaf.core.modules.support.common.platform.Platform
import kotlin.coroutines.CoroutineContext

object CommonAsyncScope: CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() {
            return when (Platform.getCurrentPlatform()) {
                Platform.BUKKIT -> BukkitAsyncDispatcher
                Platform.BUNGEECORD -> BungeeAsyncDispatcher
                else -> error("Not supported platform")
            }
        }

}