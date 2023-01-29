package net.pooleaf.core.modules.channel;

import net.pooleaf.core.Core;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channel.ChannelManager;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroupManager;
import net.pooleaf.core.modules.channel.common.channelgroup.LobbyChannelGroup;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapter;
import net.pooleaf.core.modules.channel.common.platform.ChannelAdapterFactory;
import net.pooleaf.core.modules.channel.common.redis.ChannelRedisManager;
import net.pooleaf.core.plugin.CorePlugin;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChannelModule extends CoreModule {

  public static final String MESSAGE_CHANNEL = "channel";

  @Getter
  private static ChannelManager channelManager = new ChannelManager();

  @Getter
  private static ChannelGroupManager channelGroupManager = new ChannelGroupManager();

  @Getter
  private static ChannelRedisManager redisManager;

  @Getter
  private static ChannelAdapter channelAdapter;


  @Override
  public String getName() {
    return "Channel";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support", "AnnoConfig", "RedisLib" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    redisManager = new ChannelRedisManager();
    redisManager.connect();

    channelAdapter = new ChannelAdapterFactory().createChannelAdapter();
    channelAdapter.onEnable();
  }

  @Override
  public void onDisable(CorePlugin plugin) {
    channelAdapter.onDisable();
  }

  public static String getCurrentChannelName() {
    return Core.getServerName();
  }

  public static Collection<Channel> getChannels() {
    return channelManager.getDatas().values();
  }

  public static Channel getCurrentChannel() {
    return channelAdapter.getCurrentChannel();
  }

  public static Channel getChannel(String channelName) {
    return channelManager.get(channelName);
  }

  public static Channel getChannelHasPlayer(String playerName) {
    return channelManager.getHasPlayer(playerName);
  }

  public static Channel getChannelHasPlayer(UUID uuid) {
    return channelManager.getHasPlayer(uuid);
  }

  public static Collection<ChannelGroup> getChannelGroups() {
    return channelGroupManager.getDatas().values();
  }

  public static ChannelGroup getChannelGroup(String channelGroupName) {
    return channelGroupManager.get(channelGroupName);
  }

  public static ChannelGroup getChannelGroupHasPlayer(String playerName) {
    return channelGroupManager.getHasPlayer(playerName);
  }

  public static ChannelGroup getChannelGroupHasPlayer(UUID uuid) {
    return channelGroupManager.getHasPlayer(uuid);
  }

  public static LobbyChannelGroup getLobbyChannelGroup() {
    return channelGroupManager.getLobbyChannelGroup();
  }


  public static boolean isOnline(String player) {
    for (Channel channel : getChannels()) {
      if (channel.isOnline() && channel.hasPlayer(player)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isOnline(UUID uuid) {
    for (Channel channel : getChannels()) {
      if (channel.isOnline() && channel.hasPlayer(uuid)) {
        return true;
      }
    }

    return false;
  }

  public static Set<String> getAllPlayerNames() {
    Set<String> names = new HashSet<>();

    for (Channel channel : getChannels()) {
      if (channel.isOnline()) {
        names.addAll(channel.getPlayerNames());
      }
    }

    return names;
  }

  public static Set<UUID> getAllPlayerUuids() {
    Set<UUID> uuids = new HashSet<>();

    for (Channel channel : getChannels()) {
      if (channel.isOnline()) {
        uuids.addAll(channel.getPlayerUuids());
      }
    }

    return uuids;
  }

  public static int getAllPlayerCount() {
    return getAllPlayerNames().size();
  }

}