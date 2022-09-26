package net.pooleaf.core.modules.commonscheduler.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.modules.commonscheduler.common.CommonScheduler;
import net.pooleaf.core.modules.commonscheduler.common.RepeatRunnable;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.concurrent.TimeUnit;

public class BungeeScheduler implements CommonScheduler {

    @Override
    public int runAsync(CorePlugin plugin, Runnable runnable) {
        return ProxyServer.getInstance().getScheduler()
                .runAsync((Plugin) plugin, runnable)
                .getId();
    }

    @Override
    public int runAsync(CorePlugin plugin, Runnable runnable, long delayTick) {
        return ProxyServer.getInstance().getScheduler()
                .schedule((Plugin) plugin, runnable, delayTick * 50, TimeUnit.MILLISECONDS)
                .getId();
    }

    @Override
    public int runAsync(CorePlugin plugin, Runnable runnable, long delayTick, long periodTick) {
        return ProxyServer.getInstance().getScheduler()
                .schedule((Plugin) plugin, runnable, delayTick * 50, periodTick * 50, TimeUnit.MILLISECONDS)
                .getId();
    }

    @Override
    public int runAsyncRepeat(CorePlugin plugin, Runnable runnable, long delayTick, long periodTick, int repeat) {
        return ProxyServer.getInstance().getScheduler()
                .schedule((Plugin) plugin, new RepeatRunnable(runnable, repeat), delayTick * 50, periodTick * 50, TimeUnit.MILLISECONDS)
                .getId();
    }

    @Override
    public void cancel(int taskId) {
        ProxyServer.getInstance().getScheduler().cancel(taskId);
    }

    @Override
    public boolean isRunning(int taskId) {
        throw new UnsupportedOperationException();
    }
    
}
