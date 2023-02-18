package net.pooleaf.core.modules.gui;

import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGuiManager;
import net.pooleaf.core.modules.gui.bukkit.quickbar.QuickBarManager;
import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.gui.bukkit.sign.SignGuiManager;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;
import org.bukkit.Bukkit;

public class GuiModule extends CoreModule {

  @Getter
  private static InventoryGuiManager inventoryGuiManager;

  @Getter
  private static SignGuiManager signGuiManager;

  @Getter
  private static QuickBarManager quickBarManager;


  @Override
  public String getName() {
    return "Gui";
  }

  @Override
  public String[] getDepends() {
    return new String[] { "Support", "EventSupport" };
  }

  @Override
  public void onEnable(CorePlugin plugin) {
    if (Platform.getCurrentPlatform() != Platform.BUKKIT) return;

    inventoryGuiManager = new InventoryGuiManager();
    signGuiManager = new SignGuiManager();
    quickBarManager = new QuickBarManager();

    signGuiManager.registerPacketListener();
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
