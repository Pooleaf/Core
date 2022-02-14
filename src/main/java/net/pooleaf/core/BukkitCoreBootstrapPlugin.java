package net.pooleaf.core;

import net.pooleaf.core.plugin.BukkitCorePlugin;

public class BukkitCoreBootstrapPlugin extends BukkitCorePlugin {

  @Override
  public void onStart() {
    setPrefix("§e[ Core ]");
    registerLoggerPrefix();
    registerMessagerPrefix();

    Core.init(this);

    registerEventListeners();
    registerCommands();

    Core.getCoreSqlManager().connect();
  }

}
