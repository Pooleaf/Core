package net.pooleaf.core.modules.channel.bukkit.platform;

import java.util.UUID;
import java.util.stream.Collectors;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channel.ChannelStatus;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;
import lombok.Getter;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.bukkit.tasks.BukkitChannelTpsInfoUpdateTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitChannelAdapter implements ChannelAdapter {

  @Getter
  private BukkitChannelTpsInfoUpdateTask bukkitChannelTpsInfoUpdateTask;


  @Override
  public void onEnable() {
    // 불러오기
    ChannelModule.getRedisManager().channel().loadAllChannels();
    ChannelModule.getRedisManager().channelGroup().loadAllGroups();

    // 현재 채널 정보 저장
    Channel channel = ChannelModule.getChannelManager().getOrMake(ChannelModule.getCurrentChannelName(), new Channel(ChannelModule.getCurrentChannelName()));

    channel.setOnline(true);
    channel.setChannelStatus(ChannelStatus.PREPARING);
    channel.setPlayerCount(Bukkit.getOnlinePlayers().size());
    channel.setMaxPlayerCount(Bukkit.getMaxPlayers());
    channel.setPlayerNames(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toSet()));
    channel.setPlayerUuids(Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toSet()));
    channel.save();

    // 서버 실행 완료 후 채널 정보 업데이트
    Bukkit.getScheduler().runTask((Plugin) Core.getPlugin(), () -> {
      channel.setTps(Bukkit.spigot().getTPS()[0]);
      channel.setChannelStatus(ChannelStatus.RUNNING);
      channel.setAllowFastJoin(true);
      channel.save();

      // 10초마다 TPS 업데이트
      bukkitChannelTpsInfoUpdateTask = new BukkitChannelTpsInfoUpdateTask();
      bukkitChannelTpsInfoUpdateTask.runTaskTimerAsynchronously((Plugin) Core.getPlugin(), 200L, 200L);
    });
  }

  @Override
  public void onDisable() {
    getCurrentChannel().setPlayerCount(0);
    getCurrentChannel().setMaxPlayerCount(0);
    getCurrentChannel().getPlayerNames().clear();
    getCurrentChannel().getPlayerUuids().clear();
    getCurrentChannel().getDatas().clear();
    getCurrentChannel().setChannelStatus(ChannelStatus.OFFLINE);
    getCurrentChannel().setOnline(false);
    getCurrentChannel().save();
  }

  @Override
  public Channel getCurrentChannel() {
    return ChannelModule.getChannel(Core.getServerName());
  }

  @Override
  public boolean join(String channelName, String playerName) {
    ChannelModule.getRedisManager().sendToBungeeCord(ChannelModule.MESSAGE_CHANNEL
        , "JoinByPlayerName", channelName, playerName);

    return true;
  }

  @Override
  public boolean join(String channelName, UUID uuid) {
    ChannelModule.getRedisManager().sendToBungeeCord(ChannelModule.MESSAGE_CHANNEL
        , "JoinByPlayerUuid", channelName, uuid);

    return true;
  }

  @Override
  public void broadcast(String channelName, String senderName, String message) {
    // 없는 채널이면 실행 안함
    if (ChannelModule.getChannel(channelName) == null) {
      return;
    }

    // 번지코드로 보내기 (로그용)
    ChannelModule.getRedisManager().sendToBungeeCord(ChannelModule.MESSAGE_CHANNEL
        , "Broadcast", channelName, senderName, message);
    // 채널로 보내기
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL
        , "Broadcast", senderName, message);
  }

  @Override
  public void remoteCommand(String channelName, String senderName, String commandLine) {
    // 없는 채널이면 실행 안함
    if (ChannelModule.getChannel(channelName) == null) {
      return;
    }

    // 번지코드로 보내기 (로그용)
    ChannelModule.getRedisManager().sendToBungeeCord(ChannelModule.MESSAGE_CHANNEL
        , "RemoteCommand", channelName, senderName, commandLine);
    // 채널로 보내기
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL
        , "RemoteCommand", senderName, commandLine);
  }

  @Override
  public void sendData(String channelName, String task, Object... datas) {
    // 채널로 보내기
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL
        , "SendData", task, datas);
  }

}
