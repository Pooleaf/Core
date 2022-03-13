package net.pooleaf.core;

import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.plugin.BukkitCorePlugin;
import org.bukkit.Bukkit;

public class BukkitCoreBootstrapPlugin extends BukkitCorePlugin {

  @Override
  public void onStart() {
    setPrefix("§e[ Core ]");
    setColor(CommonChatColor.YELLOW);
    registerLoggerPrefix();
    registerMessagerPrefix();

    Debugger.addListener(Bukkit.getConsoleSender());

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
