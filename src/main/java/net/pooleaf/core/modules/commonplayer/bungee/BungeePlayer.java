package net.pooleaf.core.modules.commonplayer.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;

public class BungeePlayer extends CommonPlayer<ProxiedPlayer> {

  @Override
  public ProxiedPlayer getPlatformPlayer() {
    return ProxyServer.getInstance().getPlayer(uuid);
  }

  @Override
  public void kickPlayer(String message) {
    getPlatformPlayer().disconnect(message);
  }

}
