package net.pooleaf.core.modules.commonsender.bukkit;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonsender.CommonSenderAdapter;
import net.pooleaf.core.modules.commonsender.bukkit.listener.BukkitPlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitPlayerAdapter extends CommonSenderAdapter<BukkitPlayer, BukkitConsoleSender> {

  @Override
  public void registerListeners() {
    Bukkit.getPluginManager().registerEvents(new BukkitPlayerListener(),(Plugin) Core.getPlugin());
  }

}
