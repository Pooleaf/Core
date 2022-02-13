package net.pooleaf.core.modules.eventsupport.common;

import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.eventsupport.bukkit.BukkitEventRegisterer;
import net.pooleaf.core.modules.eventsupport.bungee.BungeeEventRegisterer;
import net.pooleaf.core.modules.support.common.platform.Platform;

@UtilityClass
public class EventRegistererFactory {

    public static EventRegisterer createEventRegisterer() {
        switch (Platform.getCurrentPlatform()) {
            case BUKKIT:
                return new BukkitEventRegisterer();
            case BUNGEECORD:
                return new BungeeEventRegisterer();
        }

        return null;
    }

}
