package net.pooleaf.core;

import net.pooleaf.core.plugin.BungeeCorePlugin;

public class BungeeCoreBootstrapPlugin extends BungeeCorePlugin {

  @Override
  public void onStart() {
    Core.init(this);

    setPrefix("§e[ Core ]");
    registerLoggerPrefix();
    registerMessagerPrefix();

    registerEventListeners();
    registerCommands();

    Core.getSqlManager().connect();
    Core.getRedisManager().connect();
  }

}
