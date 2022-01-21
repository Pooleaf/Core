package net.pooleaf.core.modules.annocommand;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

public class AnnoCommandModule extends CoreModule {

  @Getter
  private static CommandManager commandManager = new CommandManager();


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
    commandManager.init(plugin);
    commandManager.registerCommands(plugin);
  }

}
