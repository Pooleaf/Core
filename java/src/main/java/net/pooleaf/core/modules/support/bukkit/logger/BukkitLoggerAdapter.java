package net.pooleaf.core.modules.support.bukkit.logger;

import net.pooleaf.core.modules.support.common.logger.LoggerAdapter;
import org.bukkit.Bukkit;

public class BukkitLoggerAdapter implements LoggerAdapter {

  @Override
  public void log(Object message) {
    Bukkit.getConsoleSender().sendMessage(message.toString());
  }

}
