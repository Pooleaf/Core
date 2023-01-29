package net.pooleaf.core.modules.support.bungee.logger;

import net.pooleaf.core.modules.support.common.logger.LoggerAdapter;
import net.md_5.bungee.api.ProxyServer;

public class BungeeLoggerAdapter implements LoggerAdapter {

  @Override
  public void log(Object message) {
    ProxyServer.getInstance().getConsole().sendMessage(message.toString());
  }

}
