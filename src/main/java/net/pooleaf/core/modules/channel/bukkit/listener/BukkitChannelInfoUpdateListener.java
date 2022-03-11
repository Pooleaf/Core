package net.pooleaf.core.modules.channel.bukkit.listener;

import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitChannelInfoUpdateListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Channel channel = ChannelModule.getCurrentChannel();
    channel.setPlayerCount(Bukkit.getOnlinePlayers().size());
    channel.setMaxPlayerCount(Bukkit.getMaxPlayers());
    channel.save();
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Channel channel = ChannelModule.getCurrentChannel();
    channel.setPlayerCount(Bukkit.getOnlinePlayers().size());
    channel.setMaxPlayerCount(Bukkit.getMaxPlayers());
    channel.save();
  }

}
