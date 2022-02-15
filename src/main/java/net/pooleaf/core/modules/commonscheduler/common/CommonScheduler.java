package net.pooleaf.core.modules.commonscheduler.common;

import net.pooleaf.core.plugin.CorePlugin;

public interface CommonScheduler {

    int runAsync(CorePlugin plugin, Runnable runnable);

    int runAsync(CorePlugin plugin, Runnable runnable, long delayTick);

    int runAsync(CorePlugin plugin, Runnable runnable, long delayTick, long periodTick);

    void cancel(int taskId);

    boolean isRunning(int taskId);

}
