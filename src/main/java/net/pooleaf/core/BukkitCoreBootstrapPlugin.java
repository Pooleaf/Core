package net.pooleaf.core;

import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.plugin.BukkitCorePlugin;
import org.bukkit.Bukkit;

public class BukkitCoreBootstrapPlugin extends BukkitCorePlugin {

  @Override
  public void onStart() {
    setPrefix("§e[ Core ]");
    registerLoggerPrefix();
    registerMessagerPrefix();

    Core.init(this);

    Core.getCoreSqlManager().connect();
  }

}
