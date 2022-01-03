package net.pooleaf.core.support.bungee.logger;

import net.md_5.bungee.api.ProxyServer;
import net.pooleaf.core.support.common.logger.LoggerAdapter;

public class BungeeLoggerAdapter implements LoggerAdapter {

  @Override
  public void log(Object message) {
    ProxyServer.getInstance().getConsole().sendMessage(message.toString());
  }

}
