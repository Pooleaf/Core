package net.pooleaf.core.support.common.logger;

import net.pooleaf.core.support.bukkit.logger.BukkitLoggerAdapter;
import net.pooleaf.core.support.bungee.logger.BungeeLoggerAdapter;
import net.pooleaf.core.support.common.platform.Platform;

public class LoggerInitializer {

  public static void init() {
    switch (Platform.getCurrentPlatform()) {
      case BUKKIT:
        Logger.setLoggerAdapter(new BukkitLoggerAdapter()); break;
      case BUNGEECORD:
        Logger.setLoggerAdapter(new BungeeLoggerAdapter()); break;
    }
  }

}
