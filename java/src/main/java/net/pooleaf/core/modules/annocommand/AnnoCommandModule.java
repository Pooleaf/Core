package net.pooleaf.core.modules.annocommand;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.annocommand.common.CommandManager;
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
  }


  /**
   * 해당 CorePlugin의 모든 Command를 등록합니다.
   * @param plugin Command를 등록할 CorePlugin
   */
  public static void registerCommands(CorePlugin plugin) {
    commandManager.registerCommands(plugin);
  }

}