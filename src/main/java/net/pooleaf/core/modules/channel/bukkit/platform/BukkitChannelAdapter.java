package net.pooleaf.core.modules.channel.bukkit.platform;

import java.util.UUID;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;

public class BukkitChannelAdapter implements ChannelAdapter {

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
