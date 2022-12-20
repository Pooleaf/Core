package net.pooleaf.core.modules.channel.bukkit.listeners;

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
    channel.setTps(Bukkit.spigot().getTPS()[0]);
    channel.setPlayerCount(Bukkit.getOnlinePlayers().size());
    channel.setMaxPlayerCount(Bukkit.getMaxPlayers());
    channel.getPlayerNames().add(event.getPlayer().getName());
    channel.getPlayerUuids().add(event.getPlayer().getUniqueId());
    channel.save();
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Channel channel = ChannelModule.getCurrentChannel();
    channel.setTps(Bukkit.spigot().getTPS()[0]);
    channel.setPlayerCount(Bukkit.getOnlinePlayers().size() - 1);
    channel.setMaxPlayerCount(Bukkit.getMaxPlayers());
    channel.getPlayerNames().remove(event.getPlayer().getName());
    channel.getPlayerUuids().remove(event.getPlayer().getUniqueId());
    channel.save();
  }

}
