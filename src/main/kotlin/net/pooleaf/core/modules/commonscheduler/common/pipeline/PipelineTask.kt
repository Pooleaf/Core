package net.pooleaf.core.modules.commonscheduler.common.pipeline

import net.pooleaf.core.plugin.CorePlugin

abstract class PipelineTask(
    val plugin: CorePlugin,
    val delayTick: Long = 0,
    val resolve: (result: Any?) -> Any?
) {

    abstract fun run(result: Any? = Unit): Any?

}