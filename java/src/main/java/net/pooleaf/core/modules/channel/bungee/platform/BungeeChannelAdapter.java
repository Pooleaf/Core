package net.pooleaf.core.modules.channel.bungee.platform;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.channel.ChannelModule;
import net.pooleaf.core.modules.channel.bungee.listeners.BungeeLobbyOnConnectListener;
import net.pooleaf.core.modules.channel.bungee.offlinecheck.ChannelOfflineCheckTask;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;
import net.pooleaf.core.modules.commonconfig.CommonConfigModule;
import net.pooleaf.core.modules.commonconfig.common.CommonConfig;
import net.pooleaf.core.modules.commonscheduler.CommonSchedulerModule;
import net.pooleaf.core.modules.support.common.logger.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BungeeChannelAdapter implements ChannelAdapter {

  @Override
  public void onEnable() {
    // 설정 불러오기
    CommonConfig channelConfig = CommonConfigModule.createConfig(new File(Core.getPlugin().getDataFolder(), "channel-config.yml")).load();

    // 오프라인 체크 타이머 설정
    channelConfig.setDefault("오프라인 체크.사용", true);
    channelConfig.setDefault("오프라인 체크.간격(초)", 10);
    channelConfig.setDefault("접속 시 로비로 이동", true);

    channelConfig.save();

    boolean useOfflineCheck = channelConfig.getBoolean("오프라인 체크.사용");
    int offlineCheckIntervalSeconds = channelConfig.getInt("오프라인 체크.간격(초)");
    boolean gotoLobbyOnConnect = channelConfig.getBoolean("접속 시 로비로 이동");

    // 오프라인 체크 Task 시작
    if (useOfflineCheck) {
      CommonSchedulerModule.getScheduler().runAsync(Core.getPlugin(), new ChannelOfflineCheckTask(), offlineCheckIntervalSeconds * 20, offlineCheckIntervalSeconds * 20);
    }

    // Redis에서 채널, 채널그룹 불러오기
    ChannelModule.getRedisManager().channel().loadAllChannels();
    ChannelModule.getRedisManager().channelGroup().loadAllGroups();

    // BungeeCord ServerInfo에 등록된 서버 이름 수집
    Set<String> registeredServerNames = new HashSet<>();
    for (ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
      registeredServerNames.add(serverInfo.getName());
    }

    // 새로운 채널만 생성 (Redis에 이미 있는 채널은 기존 데이터 유지)
    for (String channelName : registeredServerNames) {
      if (ChannelModule.getChannelManager().exists(channelName)) {
        continue;
      }

      Channel channel = new Channel(channelName);
      channel.save();

      ChannelModule.getChannelManager().set(channelName, channel);
    }

    // BungeeCord ServerInfo에 등록되지 않은 채널만 Redis에서 삭제
    ChannelModule.getRedisManager().channel().removeUnregisteredChannels(registeredServerNames);

    // 접속 시 로비 이동 Listener 등록
    if (gotoLobbyOnConnect) {
      ProxyServer.getInstance().getPluginManager().registerListener((Plugin) Core.getPlugin(), new BungeeLobbyOnConnectListener());
    }
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
    ChannelModule.getRedisManager().send(channelName, ChannelModule.MESSAGE_CHANNEL
            , "SendData", task, datas);
  }

}
