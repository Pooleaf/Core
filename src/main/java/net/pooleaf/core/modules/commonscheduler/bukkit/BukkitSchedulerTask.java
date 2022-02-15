package net.pooleaf.core.modules.commonscheduler.bukkit;

import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;
import net.pooleaf.core.modules.commonscheduler.common.CommonSchedulerTask;
import net.pooleaf.core.plugin.CorePlugin;

public abstract class BukkitSchedulerTask extends CommonSchedulerTask {

    public BukkitSchedulerTask(CorePlugin plugin) {
        super(plugin);
    }


    public BukkitSchedulerTask runSync() {
        taskId = CommonSchedulerModule.bukkit().getScheduler().runSync(plugin, this);
        return this;
    }

    public BukkitSchedulerTask runSync(long delayTick) {
        taskId = CommonSchedulerModule.bukkit().getScheduler().runSync(plugin, this, delayTick);
        return this;
    }

    public BukkitSchedulerTask runSync(long delayTick, long periodTick) {
        taskId = CommonSchedulerModule.bukkit().getScheduler().runSync(plugin, this, delayTick, periodTick);
        return this;
    }
}
