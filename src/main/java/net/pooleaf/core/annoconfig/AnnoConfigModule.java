package net.pooleaf.core.annoconfig;

import net.pooleaf.core.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.annoconfig.serializer.bukkit.LocationSerializer;
import net.pooleaf.core.support.common.platform.Platform;

public class AnnoConfigModule extends CoreModule {

  @Override
  public String getName() {
    return "AnnoConfig";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support", "PlatformConfig" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    // 버킷 기본 Serializer
    if (Platform.getCurrentPlatform() == Platform.BUKKIT) {
      AnnoConfig.getDefaultSerializer().put(org.bukkit.Location.class, LocationSerializer.class);
    }
  }

}
