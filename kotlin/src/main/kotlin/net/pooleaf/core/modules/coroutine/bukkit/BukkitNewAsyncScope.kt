package net.pooleaf.core.modules.coroutine.bukkit

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

object BukkitNewAsyncScope: CoroutineScope {

    override val coroutineContext: CoroutineContext = BukkitNewAsyncDispatcher

}