package net.pooleaf.core.modules.coroutine.bukkit

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import net.pooleaf.core.Core
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.coroutines.CoroutineContext

object BukkitSyncDispatcher: CoroutineDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val plugin = Core.getPluginManager().currentPlugin ?: Core.getPlugin()

        if (Bukkit.isPrimaryThread()) block.run()
        else Bukkit.getScheduler().runTask(plugin as JavaPlugin, block)
    }

}