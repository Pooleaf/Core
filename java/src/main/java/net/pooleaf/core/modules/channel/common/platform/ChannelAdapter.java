package net.pooleaf.core.modules.channel.common.platform;

import java.util.UUID;

import net.pooleaf.core.modules.channel.common.channel.Channel;

public interface ChannelAdapter {

  void onEnable();

  void onDisable();

  Channel getCurrentChannel();

  boolean join(String channelName, String playerName);

  boolean join(String channelName, UUID uuid);

  void broadcast(String channelName, String senderName, String message);

  void remoteCommand(String channelName, String senderName, String commandLine);

  void sendData(String channelName, String task, Object... datas);

}