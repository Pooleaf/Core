package net.pooleaf.core.modules.gui;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.gui.quickbar.QuickBarManager;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.modules.gui.inventory.InventoryGuiManager;
import net.pooleaf.core.modules.gui.inventory.event.InventoryGuiListener;
import net.pooleaf.core.modules.gui.quickbar.event.QuickBarListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class GuiModule extends CoreModule {

  @Getter
  private static InventoryGuiManager inventoryGuiManager = new InventoryGuiManager();

  @Getter
  private static QuickBarManager quickBarManager = new QuickBarManager();


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
    Bukkit.getPluginManager().registerEvents(new InventoryGuiListener(), (Plugin) plugin);
    Bukkit.getPluginManager().registerEvents(new QuickBarListener(), (Plugin) plugin);
  }

  @Override
  public void onDisable(CorePlugin plugin) {
    // 열려있는 GUI 모두 닫기
    inventoryGuiManager.getDatas().keySet().stream()
            .map(uuid -> Bukkit.getPlayer(uuid))
            .filter(player -> player != null)
            .forEach(player -> player.closeInventory());
  }

}
