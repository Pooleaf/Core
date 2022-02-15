package net.pooleaf.core.modules.commonscheduler.common;

import lombok.Data;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;
import net.pooleaf.core.plugin.CorePlugin;

@Data
public abstract class CommonSchedulerTask implements Runnable {

    protected final CorePlugin plugin;

    protected Integer taskId;


    public CommonSchedulerTask runAsync() {
        taskId = CommonSchedulerModule.getScheduler().runAsync(plugin, this);
        return this;
    }

    public CommonSchedulerTask runAsync(long delayTick) {
        taskId = CommonSchedulerModule.getScheduler().runAsync(plugin, this, delayTick);
        return this;
    }

    public CommonSchedulerTask runAsync(long delayTick, long periodTick) {
        taskId = CommonSchedulerModule.getScheduler().runAsync(plugin, this, delayTick, periodTick);
        return this;
    }

    public void cancel() {
        if (taskId == null) {
            return;
        }

        CommonSchedulerModule.getScheduler().cancel(taskId);
        taskId = null;
    }

}
