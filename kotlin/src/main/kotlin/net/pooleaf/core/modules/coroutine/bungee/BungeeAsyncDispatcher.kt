package net.pooleaf.core.modules.coroutine.bungee

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import net.pooleaf.core.Core
import kotlin.coroutines.CoroutineContext

object BungeeAsyncDispatcher: CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val plugin = Core.getPluginManager().currentPlugin ?: Core.getPlugin()

        ProxyServer.getInstance().scheduler.runAsync(plugin as Plugin, block)
    }

}