package net.pooleaf.core.modules.coroutine.bukkit

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class BukkitAsyncScope: CoroutineScope {

    override val coroutineContext: CoroutineContext = BukkitAsyncDispatcher

}