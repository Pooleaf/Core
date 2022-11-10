package net.pooleaf.core.modules.commonscheduler.common.pipeline

import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule
import net.pooleaf.core.plugin.CorePlugin
import java.util.concurrent.CompletableFuture

class AsyncTask(
    plugin: CorePlugin,
    delayTick: Long = 0,
    resolve: (result: Any?) -> Any?
): PipelineTask(
    plugin,
    delayTick,
    resolve
) {

    override fun run(result: Any?): Any? {
        val future = CompletableFuture<Any>()

        CommonSchedulerModule.bukkit().scheduler.runAsync(plugin, {
            val value = resolve(result)
            future.complete(value)
        }, delayTick)

        return future.get()
    }

}