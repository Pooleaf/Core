package net.pooleaf.core.modules.commonscheduler.common.pipeline

import net.pooleaf.core.Core

fun sync(delay: Long = 0, resolve: (Any?) -> Any?): SchedulerPipeline {
    val plugin = Core.getPluginManager().currentPlugin ?: Core.getPlugin()
    return SchedulerPipeline(plugin).thenSync(delay, resolve)
}

fun async(delay: Long = 0, resolve: (Any?) -> Any?): SchedulerPipeline {
    val plugin = Core.getPluginManager().currentPlugin ?: Core.getPlugin()
    return SchedulerPipeline(plugin).thenAsync(delay, resolve)
}