package net.pooleaf.core.modules.commonscheduler.common.pipeline

import net.pooleaf.core.plugin.CorePlugin

class SchedulerPipeline(val plugin: CorePlugin) {

    val tasks: ArrayList<PipelineTask> = ArrayList()

    private var catch: ((exception: Exception) -> Unit)? = null


    fun thenSync(delay: Long = 0, resolve: (result:Any?) -> Any?): SchedulerPipeline {
        tasks.add(SyncTask(plugin, delay, resolve))
        return this
    }

    fun thenAsync(delay: Long = 0, resolve: (result:Any?) -> Any?): SchedulerPipeline {
        tasks.add(AsyncTask(plugin, delay, resolve))
        return this
    }

    fun catch(catch: (exception: Exception) -> Unit): SchedulerPipeline {
        this.catch = catch
        return this
    }


    fun run() {
        var currentTask: PipelineTask? = null
        var result: Any? = Unit

        try {
            tasks.forEach {
                currentTask = it
                result = it.run(result)
            }
        } catch (exception: Exception) {
            catch?.let { it(exception) }
        }
    }

}