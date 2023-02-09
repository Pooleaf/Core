package net.pooleaf.core.modules.commonsender.bungee;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeePlayer extends CommonPlayer<ProxiedPlayer> {

  @Override
  public void setDisplayName(String displayName) {
    super.setDisplayName(displayName);

    // 온라인일 경우 실제 닉네임도 변경
    if (isOnline()) {
      getPlatformSender().setDisplayName(displayName);
    }
  }

  @Override
  public ProxiedPlayer getPlatformSender() {
    return ProxyServer.getInstance().getPlayer(uuid);
  }

  @Override
  public boolean isOnline() {
    return getPlatformSender() != null && getPlatformSender().isConnected();
  }

  @Override
  public boolean hasPermission(String permission) {
    return getPlatformSender().hasPermission("*") || getPlatformSender().hasPermission(permission);
  }

  @Override
  public void kickPlayer(String message) {
    getPlatformSender().disconnect(message);
  }

}
