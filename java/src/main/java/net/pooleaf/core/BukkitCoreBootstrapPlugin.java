package net.pooleaf.core;

import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.plugin.BukkitCorePlugin;
import lombok.Getter;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
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

    Core.init(this);

    registerEventListeners();
    Logger.log("EventListener가 등록되었습니다.");

    registerCommonEventListeners();
    Logger.log("CommonEventListener가 등록되었습니다.");

    registerCommands();
    Logger.log("명령어가 등록되었습니다.");
  }

  @Override
  public void onEnd() {
    Core.getModuleManager().endModules();
  }

}
