package net.pooleaf.core.event;

import net.pooleaf.core.event.bukkit.BukkitEventRegisterer;
import net.pooleaf.core.event.bungee.BungeeEventRegisterer;
import net.pooleaf.core.support.common.platform.Platform;

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
