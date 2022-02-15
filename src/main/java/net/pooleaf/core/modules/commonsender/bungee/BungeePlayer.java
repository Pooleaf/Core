package net.pooleaf.core.modules.commonsender.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;

public class BungeePlayer extends CommonPlayer<ProxiedPlayer> {

  @Override
  public ProxiedPlayer getPlatformSender() {
    return ProxyServer.getInstance().getPlayer(uuid);
  }

  @Override
  public void kickPlayer(String message) {
    getPlatformSender().disconnect(message);
  }

}
