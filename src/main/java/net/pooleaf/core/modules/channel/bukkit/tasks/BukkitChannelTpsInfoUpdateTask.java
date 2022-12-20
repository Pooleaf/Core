package net.pooleaf.core.modules.channel.bukkit.tasks;

import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BukkitChannelTpsInfoUpdateTask extends BukkitRunnable {

  @Override
  public void run() {
    Channel channel = ChannelModule.getCurrentChannel();
    channel.setTps(Bukkit.spigot().getTPS()[0]);
    channel.save();
  }

}