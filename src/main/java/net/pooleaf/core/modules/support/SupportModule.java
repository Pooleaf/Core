package net.pooleaf.core.modules.support;

import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.modules.support.bukkit.nms.NmsDetector;
import net.pooleaf.core.modules.support.common.logger.LoggerInitializer;
import net.pooleaf.core.modules.support.common.platform.PlatformDetector;

public class SupportModule extends CoreModule {

  @Override
  public String getName() {
    return "Support";
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    // Nms
    NmsDetector.detectNmsVersion();

    // Platform
    PlatformDetector.detectPlatform();

    // Logger
    LoggerInitializer.init();
  }

}