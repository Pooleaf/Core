package net.pooleaf.core.test;

import net.pooleaf.core.modules.annocommand.common.Command;
import net.pooleaf.core.modules.annocommand.common.CommandResult;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryIcon;
import net.pooleaf.core.modules.support.bukkit.util.ItemBuilder;
import net.pooleaf.core.modules.support.common.logger.Logger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCommand {

  @Command(
      name = {"test"}
  )
  public static void test(Player player, CommandResult result) {
    InventoryGui gui = new InventoryGui("Test Gui", 3);
    InventoryIcon icon = new InventoryIcon() {
      @Override
      protected ItemStack updateItem() {
        return new ItemBuilder(Material.GRASS).build();
      }
    };
    for (int i = 1; i <= 9; i++) {
      gui.getMainPanel().set(i, 1, icon);
      gui.getMainPanel().set(i, 3, icon);
    }
    gui.getMainPanel().set(1, 2, icon);
    gui.getMainPanel().set(9, 2, icon);
    gui.updateAsynchronously();

    Logger.log(gui);

    gui.open(player);
  }

}
