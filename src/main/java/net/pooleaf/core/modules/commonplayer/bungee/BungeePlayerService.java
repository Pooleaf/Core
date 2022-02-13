package net.pooleaf.core.modules.commonplayer.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonplayer.CommonPlayerService;
import net.pooleaf.core.modules.commonplayer.bungee.listener.BungeePlayerListener;

public class BungeePlayerService extends CommonPlayerService<BungeePlayer> {

  @Override
  public void registerListeners() {
    ProxyServer.getInstance().getPluginManager().registerListener((Plugin) Core.getPlugin(), new BungeePlayerListener());
  }

}
