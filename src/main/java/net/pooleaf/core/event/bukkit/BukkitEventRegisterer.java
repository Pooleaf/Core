package net.pooleaf.core.event.bukkit;

import net.pooleaf.core.Core;
import net.pooleaf.core.event.EventRegisterer;
import net.pooleaf.core.event.bukkit.listener.PlayerDamageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitEventRegisterer implements EventRegisterer {

    @Override
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), (Plugin) Core.getPlugin());
    }

}
