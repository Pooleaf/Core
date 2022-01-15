package net.pooleaf.core;

import net.pooleaf.core.plugin.BukkitCorePlugin;

public class BukkitCoreBootstrapPlugin extends BukkitCorePlugin {

  @Override
  public void onStart() {
    Core.init(this);
    setPrefix("§e[ Core ] §f");
  }

}
