package net.pooleaf.core.test;

import net.pooleaf.core.annocommand.Command;
import net.pooleaf.core.annocommand.CommandResult;
import net.pooleaf.core.gui.inventory.Gui;
import net.pooleaf.core.gui.inventory.Icon;
import net.pooleaf.core.support.bukkit.util.ItemBuilder;
import net.pooleaf.core.support.common.logger.Logger;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestCommand {

  @Command(
      name = {"test"}
  )
  public static void test(Player player, CommandResult result) {
    Gui gui = new Gui("Test Gui", 3);
    Icon icon = new Icon() {
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
