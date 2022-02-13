package net.pooleaf.core.modules.commonplayer.bukkit;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonplayer.CommonPlayerService;
import net.pooleaf.core.modules.commonplayer.bukkit.listener.BukkitPlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitPlayerService extends CommonPlayerService<BukkitPlayer> {

  @Override
  public void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new BukkitPlayerListener(),(Plugin) Core.getPlugin());
  }

}
