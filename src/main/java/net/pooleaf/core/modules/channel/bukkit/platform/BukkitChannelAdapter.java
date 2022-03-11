package net.pooleaf.core.modules.channel.bukkit.platform;

import java.util.UUID;
import java.util.stream.Collectors;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channel.ChannelStatus;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BukkitChannelAdapter implements ChannelAdapter {

  @Override
  public void onEnable() {
    Channel channel = getCurrentChannel();
    channel.setOnline(true);
    channel.setChannelStatus(ChannelStatus.PREPARING);
    channel.setPlayerCount(Bukkit.getOnlinePlayers().size());
    channel.setPlayerNames(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
    channel.setPlayerUuids(Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList()));
    channel.save();

    // 현재 채널 정보 저장
    Bukkit.getScheduler().runTask((Plugin) Core.getPlugin(), () -> {
      channel.setChannelStatus(ChannelStatus.RUNNING);
      channel.setAllowFastJoin(true);
      channel.save();
    });
  }

  @Override
  public Channel getCurrentChannel() {
    return ChannelModule.getChannel(ChannelModule.getRedisManager().getConfig().getServerName());
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
  public void remoteCommand(String channelName, String senderName, String commandLine) {
    ChannelModule.getRedisManager().sendToBungeeCord(ChannelModule.MESSAGE_CHANNEL
        , "RemoteCommand", channelName, senderName, commandLine);
  }

  @Override
  public void sendData(String channelName, String task, Object... datas) {
    ChannelModule.getRedisManager().sendToBungeeCord(ChannelModule.MESSAGE_CHANNEL
        , "SendData", channelName, task, datas);
  }

}
