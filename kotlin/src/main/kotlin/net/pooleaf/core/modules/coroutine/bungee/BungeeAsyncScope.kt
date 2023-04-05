package net.pooleaf.core.modules.coroutine.bungee

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

object BungeeAsyncScope: CoroutineScope {

    override val coroutineContext: CoroutineContext = BungeeAsyncDispatcher

}