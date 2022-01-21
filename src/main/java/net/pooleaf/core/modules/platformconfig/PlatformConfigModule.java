package net.pooleaf.core.modules.platformconfig;

import net.pooleaf.core.module.CoreModule;

public class PlatformConfigModule extends CoreModule {

  @Override
  public String getName() {
    return "PlatformConfig";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support" };
  }

}