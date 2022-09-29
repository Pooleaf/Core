package net.pooleaf.core;

import lombok.Getter;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.plugin.BukkitCorePlugin;
import org.bukkit.Bukkit;

public class BukkitCoreBootstrapPlugin extends BukkitCorePlugin {

  @Getter
  private static BukkitCoreBootstrapPlugin instance;


  @Override
  public void onStart() {
    instance = this;

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
