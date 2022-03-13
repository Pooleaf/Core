package net.pooleaf.core;

import net.pooleaf.core.plugin.BungeeCorePlugin;

public class BungeeCoreBootstrapPlugin extends BungeeCorePlugin {

  @Override
  public void onStart() {
    setPrefix("§e[ Core ]");
    registerLoggerPrefix();
    registerMessagerPrefix();

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
