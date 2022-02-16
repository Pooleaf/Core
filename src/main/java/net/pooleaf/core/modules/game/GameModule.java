package net.pooleaf.core.modules.game;

import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

public class GameModule extends CoreModule {

  @Override
  public String getName() {
    return "Game";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
  }

}
