package net.pooleaf.core.gui;

import net.pooleaf.core.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.gui.inventory.GuiManager;
import net.pooleaf.core.gui.inventory.event.GuiListener;
import net.pooleaf.core.gui.quickbar.event.QuickBarListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class GuiModule extends CoreModule {

  @Override
  public String getName() {
    return "Gui";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support", "Event" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    Bukkit.getPluginManager().registerEvents(new GuiListener(), (Plugin) plugin);
    Bukkit.getPluginManager().registerEvents(new QuickBarListener(), (Plugin) plugin);
  }

  @Override
  public void onDisable(CorePlugin plugin) {
    // 열려있는 GUI 모두 닫기
    GuiManager.getPlayerGuis().keySet().stream()
            .map(uuid -> Bukkit.getPlayer(uuid))
            .filter(player -> player != null)
            .forEach(player -> player.closeInventory());
  }
}
