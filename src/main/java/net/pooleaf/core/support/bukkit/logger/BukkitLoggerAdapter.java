package net.pooleaf.core.support.bukkit.logger;

import net.pooleaf.core.support.common.logger.LoggerAdapter;
import org.bukkit.Bukkit;

public class BukkitLoggerAdapter implements LoggerAdapter {

  @Override
  public void log(Object message) {
    Bukkit.getConsoleSender().sendMessage(message.toString());
  }

}
