package net.pooleaf.core.modules.commonscheduler.bukkit;

import net.pooleaf.core.modules.commonscheduler.common.CommonScheduler;
import net.pooleaf.core.plugin.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitScheduler implements CommonScheduler {

    @Override
    public int runAsync(CorePlugin plugin, Runnable runnable) {
        return Bukkit.getScheduler()
                .runTaskAsynchronously((Plugin) plugin, runnable)
                .getTaskId();
    }

    @Override
    public int runAsync(CorePlugin plugin, Runnable runnable, long delayTick) {
        return Bukkit.getScheduler()
                .runTaskLaterAsynchronously((Plugin) plugin, runnable, delayTick)
                .getTaskId();
    }

    @Override
    public int runAsync(CorePlugin plugin, Runnable runnable, long delayTick, long periodTick) {
        return Bukkit.getScheduler()
                .runTaskTimerAsynchronously((Plugin) plugin, runnable, delayTick, periodTick)
                .getTaskId();
    }

    public int runSync(CorePlugin plugin, Runnable runnable) {
        return Bukkit.getScheduler()
                .runTask((Plugin) plugin, runnable)
                .getTaskId();
    }

    public int runSync(CorePlugin plugin, Runnable runnable, long delayTick) {
        return Bukkit.getScheduler()
                .runTaskLater((Plugin) plugin, runnable, delayTick)
                .getTaskId();
    }

    public int runSync(CorePlugin plugin, Runnable runnable, long delayTick, long periodTick) {
        return Bukkit.getScheduler()
                .runTaskTimer((Plugin) plugin, runnable, delayTick, periodTick)
                .getTaskId();
    }

    @Override
    public void cancel(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public boolean isRunning(int taskId) {
        return Bukkit.getScheduler().isCurrentlyRunning(taskId);
    }

}
