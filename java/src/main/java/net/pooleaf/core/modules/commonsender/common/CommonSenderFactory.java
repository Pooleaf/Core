package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.commonsender.bukkit.BukkitConsoleSender;
import net.pooleaf.core.modules.commonsender.bukkit.BukkitSenderAdapter;
import net.pooleaf.core.modules.commonsender.bungee.BungeeConsoleSender;
import net.pooleaf.core.modules.commonsender.bungee.BungeeSenderAdapter;
import net.pooleaf.core.modules.support.common.platform.Platform;

public class CommonSenderFactory {

  public CommonSenderAdapter createCommonSenderAdapter() {
    switch (Platform.getCurrentPlatform()) {
      case BUKKIT:
        return new BukkitSenderAdapter();
      case BUNGEECORD:
        return new BungeeSenderAdapter();
    }

    return null;
  }

  public CommonConsoleSender createCommonConsoleSender() {
    switch (Platform.getCurrentPlatform()) {
      case BUKKIT:
        return new BukkitConsoleSender();
      case BUNGEECORD:
        return new BungeeConsoleSender();
    }

    return null;
  }

}
