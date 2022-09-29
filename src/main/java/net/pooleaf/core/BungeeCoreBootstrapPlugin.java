package net.pooleaf.core;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.plugin.BungeeCorePlugin;

public class BungeeCoreBootstrapPlugin extends BungeeCorePlugin {

  @Getter
  private BungeeCoreBootstrapPlugin instance;


  @Override
  public void onStart() {
    instance = this;

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
