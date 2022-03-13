package net.pooleaf.core;

import net.md_5.bungee.api.ProxyServer;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.plugin.BungeeCorePlugin;

public class BungeeCoreBootstrapPlugin extends BungeeCorePlugin {

  @Override
  public void onStart() {
    setPrefix("§e[ Core ]");
    setColor(CommonChatColor.YELLOW);
    registerLoggerPrefix();
    registerMessagerPrefix();

    Debugger.addListener(ProxyServer.getInstance().getConsole());

    Core.init(this);

    registerEventListeners();
    registerCommonEventListeners();
    registerCommands();
  }

  @Override
  public void onEnd() {
    Core.getModuleManager().endModules();
  }

}
