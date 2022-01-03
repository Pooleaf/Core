package net.pooleaf.core.annocommand;

import net.pooleaf.core.CoreModule;
import net.pooleaf.core.CorePlugin;

public class AnnoCommandModule extends CoreModule {

  @Override
  public String getName() {
    return "AnnoCommand";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    CommandManager.init(plugin);
    CommandManager.registerCommands(plugin);
  }

}
