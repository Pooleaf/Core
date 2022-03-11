package net.pooleaf.core.modules.channel.bungee.platform;

import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;
import net.pooleaf.core.modules.channel.common.channel.Channel;

public class BungeeChannelAdapter implements ChannelAdapter {

  private boolean join(String channelName, ProxiedPlayer player) {
    if (player == null) {
      return false;
    }

    Channel channel = ChannelModule.getChannel(channelName);
    if (channel == null) {
      return false;
    }

    if (!channel.canJoin()
        || channel.hasPlayer(player.getName())) {
      return false;
    }

    ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(channel.getName());
    if (serverInfo == null) {
      return false;
    }

    player.connect(serverInfo);

    return true;
  }

  @Override
  public boolean join(String channelName, String playerName) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
    return join(channelName, player);
  }

  @Override
  public boolean join(String channelName, UUID uuid) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
    return join(channelName, player);
  }

  @Override
  public void remoteCommand(String channelName, String sender, String commandLine) {
    Channel channel = ChannelModule.getChannel(channelName);
    if (channel == null) {
      return;
    }

    channel.sendData("RemoteCommand", sender, commandLine);
  }

  @Override
  public void sendData(String channelName, String task, Object... datas) {
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL, task, datas);
  }

}
