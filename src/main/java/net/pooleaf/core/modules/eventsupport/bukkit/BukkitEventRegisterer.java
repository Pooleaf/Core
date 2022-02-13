package net.pooleaf.core.modules.eventsupport.bukkit;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.eventsupport.common.EventRegisterer;
import net.pooleaf.core.modules.eventsupport.bukkit.listener.PlayerDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitEventRegisterer implements EventRegisterer {

    @Override
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), (Plugin) Core.getPlugin());
    }

}
