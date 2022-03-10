package net.pooleaf.core.modules.channel;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroupManager;
import net.pooleaf.core.modules.channel.common.channel.ChannelManager;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChannelModule extends CoreModule {

  @Getter
  private static ChannelManager channelManager = new ChannelManager();

  @Getter
  private static ChannelGroupManager channelGroupManager = new ChannelGroupManager();


  @Override
  public String getName() {
    return "Channel";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support", "AnnoConfig" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {

  }


  public static Collection<Channel> getChannels() {
    return channelManager.getDatas().values();
  }

  public static Collection<ChannelGroup> getChannelGroups() {
    return channelGroupManager.getDatas().values();
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