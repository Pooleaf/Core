package net.pooleaf.core.modules.support.common.logger;

import lombok.experimental.UtilityClass;
import net.pooleaf.core.modules.support.bukkit.logger.BukkitLoggerAdapter;
import net.pooleaf.core.modules.support.bungee.logger.BungeeLoggerAdapter;
import net.pooleaf.core.modules.support.common.platform.Platform;

@UtilityClass
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
