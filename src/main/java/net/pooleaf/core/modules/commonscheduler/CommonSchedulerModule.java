package net.pooleaf.core.modules.commonscheduler;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.pooleaf.core.Core;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.commonscheduler.bukkit.BukkitScheduler;
import net.pooleaf.core.modules.commonscheduler.bungee.BungeeScheduler;
import net.pooleaf.core.modules.commonscheduler.common.CommonScheduler;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;

public class CommonSchedulerModule extends CoreModule {

    @Getter
    private static CommonScheduler scheduler;


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
                break;
            case BUNGEECORD:
                scheduler = new BungeeScheduler();
                break;
        }
    }


    public static BukkitScheduler getBukkitScheduler() {
        Preconditions.checkArgument(scheduler instanceof BukkitScheduler, "Bukkit에서만 사용할 수 있습니다.");

        return (BukkitScheduler) scheduler;
    }

}
