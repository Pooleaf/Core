package net.pooleaf.core.modules.commonsender.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonsender.CommonSenderAdapter;
import net.pooleaf.core.modules.commonsender.bungee.listener.BungeePlayerListener;

public class BungeePlayerAdapter extends CommonSenderAdapter<BungeePlayer, BungeeConsoleSender> {

  @Override
  public void registerListeners() {
    ProxyServer.getInstance().getPluginManager().registerListener((Plugin) Core.getPlugin(), new BungeePlayerListener());
  }

}
