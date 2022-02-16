package net.pooleaf.core.modules.gui;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGuiManager;
import net.pooleaf.core.modules.gui.bukkit.quickbar.QuickBarManager;
import net.pooleaf.core.plugin.CorePlugin;
import org.bukkit.Bukkit;

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
  public void onDisable(CorePlugin plugin) {
    // 열려있는 GUI 모두 닫기
    inventoryGuiManager.getDatas().keySet().stream()
            .map(uuid -> Bukkit.getPlayer(uuid))
            .filter(player -> player != null)
            .forEach(player -> player.closeInventory());
  }

}
