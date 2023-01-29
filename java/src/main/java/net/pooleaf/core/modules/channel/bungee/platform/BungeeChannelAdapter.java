package net.pooleaf.core.modules.channel.bungee.platform;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.bungee.offlinecheck.ChannelOfflineCheckTask;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;
import net.pooleaf.core.modules.commonconfig.CommonConfigModule;
import net.pooleaf.core.modules.commonconfig.common.CommonConfig;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BungeeChannelAdapter implements ChannelAdapter {

  @Override
  public void onEnable() {
    // Redis에서 채널/채널 그룹 정보 불러오기
    ChannelModule.getRedisManager().channel().loadAllChannels();
    ChannelModule.getRedisManager().channelGroup().loadAllGroups();


    CommonConfig channelConfig = CommonConfigModule.createConfig(new File(Core.getPlugin().getDataFolder(), "channel-config.yml")).load();

    // 오프라인 체크 스케쥴러 설정
    channelConfig.setDefault("오프라인 체크.사용", true);
    channelConfig.setDefault("오프라인 체크.간격(초)", 10);

    boolean useOfflineCheck = channelConfig.getBoolean("오프라인 체크.사용");
    int offlineCheckIntervalSeconds = channelConfig.getInt("오프라인 체크.간격(초)");

    if (useOfflineCheck) {
      CommonSchedulerModule.getScheduler().runAsync(Core.getPlugin(), new ChannelOfflineCheckTask(), offlineCheckIntervalSeconds * 20, offlineCheckIntervalSeconds * 20);
    }

    // 채널 설정
    Map<String, Channel> newChannelDatas = new HashMap<>();

    for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
      channelConfig.setDefault("채널." + serverInfo.getName() + ".표기", serverInfo.getName());
      channelConfig.setDefault("채널." + serverInfo.getName() + ".그룹", "그룹1");
    }
    channelConfig.save();

    for (String key : channelConfig.getKeys("채널")) {
      // 없는 채널 건너뛰기
      if (ProxyServer.getInstance().getServerInfo(key) == null) {
        continue;
      }

      Channel channel = ChannelModule.getChannelManager().getOrMake(key, new Channel(key));
      channel.setDisplayName(channelConfig.getString("채널." + key + ".표기"));
      channel.setGroupName(channelConfig.getString("채널." + key + ".그룹"));
      channel.save();

      newChannelDatas.put(channel.getName(), channel);
    }

    ChannelModule.getChannelManager().setDatas(newChannelDatas);

    // 채널 그룹 설정
    Map<String, ChannelGroup> newChannelGroupDatas = new HashMap<>();

    if (!channelConfig.getFile().exists()) {
      channelConfig.setDefault("채널그룹.lobby.표기", "로비");
      channelConfig.save();
    }

    for (String key : channelConfig.getKeys("채널그룹")) {
      ChannelGroup channelGroup = ChannelModule.getChannelGroupManager().getOrMake(key, new ChannelGroup(key));
      channelGroup.setDisplayName(channelConfig.getString("채널그룹." + key + ".표기"));
      channelGroup.save();

      newChannelGroupDatas.put(channelGroup.getName(), channelGroup);
    }

    ChannelModule.getChannelGroupManager().setDatas(newChannelGroupDatas);

    // 사용하지 않는 채널/채널그룹 Redis에서 삭제
    ChannelModule.getRedisManager().channel().removeUnusedChannels();
    ChannelModule.getRedisManager().channelGroup().removeUnusedChannelGroups();
  }

  @Override
  public void onDisable() {

  }

  @Override
  public Channel getCurrentChannel() {
    throw new UnsupportedOperationException();
  }

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
  public void broadcast(String channelName, String senderName, String message) {
    // 없는 채널이면 실행 안함
    if (ChannelModule.getChannel(channelName) == null) {
      return;
    }

    // 로그
    Logger.nlog("§e[원격 공지] §f" + senderName+ " §e→ §f" + channelName + ": " + message);

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

    // 로그
    Logger.log("§e[원격 명령] §f" + senderName + " §e→ §f" + channelName + ": " + commandLine);

    // 채널로 보내기
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL
        , "RemoteCommand", senderName, commandLine);
  }

  @Override
  public void sendData(String channelName, String task, Object... datas) {
    // 채널로 보내기
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL, task, datas);
  }

}
