package net.pooleaf.core.modules.support;

import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.support.common.messager.MessagerInitializer;
import net.pooleaf.core.modules.support.common.platform.Platform;
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
    // Platform
    PlatformDetector.detectPlatform();

    // Nms
    if (Platform.getCurrentPlatform() == Platform.BUKKIT) {
      NmsDetector.detectNmsVersion();
    }

    // Logger
    LoggerInitializer.init();

    // Messager
    MessagerInitializer.init();
  }

}