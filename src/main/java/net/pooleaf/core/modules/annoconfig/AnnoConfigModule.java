package net.pooleaf.core.modules.annoconfig;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.modules.annoconfig.serializer.bukkit.LocationSerializer;
import net.pooleaf.core.modules.support.common.platform.Platform;

public class AnnoConfigModule extends CoreModule {

  @Getter
  private static AnnoConfig annoConfig = new AnnoConfig();


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
