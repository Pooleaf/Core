package net.pooleaf.core.modules.commonplayer.bukkit;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonplayer.CommonPlayerAdapter;
import net.pooleaf.core.modules.commonplayer.bukkit.listener.BukkitPlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitPlayerAdapter extends CommonPlayerAdapter<BukkitPlayer> {

  @Override
  public void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new BukkitPlayerListener(),(Plugin) Core.getPlugin());
  }

}
