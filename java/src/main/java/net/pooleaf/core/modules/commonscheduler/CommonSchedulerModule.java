package net.pooleaf.core.modules.commonscheduler;

import com.google.common.base.Preconditions;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonscheduler.bukkit.BukkitScheduler;
import net.pooleaf.core.modules.commonscheduler.bungee.BungeeScheduler;
import net.pooleaf.core.modules.commonscheduler.common.CommonScheduler;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;
import lombok.Getter;

public class CommonSchedulerModule extends CoreModule {

    @Getter
    private static CommonScheduler scheduler;

    private static CommonSchedulerAdapter schedulerAdapter;


    @Override
    public String getName() {
        return "CommonScheduler";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        switch (Platform.getCurrentPlatform()) {
            case BUKKIT:
                scheduler = new BukkitScheduler();
                schedulerAdapter = new CommonSchedulerAdapter<BukkitScheduler>();
                break;
            case BUNGEECORD:
                scheduler = new BungeeScheduler();
                break;
        }
    }


    public static CommonSchedulerAdapter<BukkitScheduler> bukkit() {
        Preconditions.checkArgument(Platform.getCurrentPlatform() == Platform.BUKKIT, "Bukkit 플랫폼에서만 사용할 수 있습니다.");

        return schedulerAdapter;
    }

    public static CommonSchedulerAdapter<BungeeScheduler> bungee() {
        Preconditions.checkArgument(Platform.getCurrentPlatform() == Platform.BUNGEECORD, "BungeeCord 플랫폼에서만 사용할 수 있습니다.");

        return schedulerAdapter;
    }

}
