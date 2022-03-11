package net.pooleaf.core.modules.channel.common.platform;

import net.pooleaf.core.modules.channel.bukkit.platform.BukkitChannelAdapter;
import net.pooleaf.core.modules.channel.bungee.platform.BungeeChannelAdapter;
import net.pooleaf.core.modules.support.common.platform.Platform;

public class ChannelAdapterFactory {

  public static ChannelAdapter createChannelAdapter() {
    switch (Platform.getCurrentPlatform()) {
      case BUKKIT:
        return new BukkitChannelAdapter();

      case BUNGEECORD:
        return new BungeeChannelAdapter();
    }

    return null;
  }

}
