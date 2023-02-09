package net.pooleaf.core.modules.gui.bukkit.quickbar;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class QuickBarManager extends AbstractManager<UUID, QuickBar> {

    public void setTo(Player player, QuickBar quickBar) {
        set(player.getUniqueId(), quickBar);

        player.getInventory().clear();
        quickBar.getSlots().forEach((x, slot) -> player.getInventory().setItem(x - 1, slot.getItem()));

        player.updateInventory();
        Bukkit.getScheduler().runTaskLater((Plugin) Core.getPlugin(), () -> quickBar.updateFakeIcons(), 1L);
    }

    public boolean removeTo(Player player) {
        if (!exists(player.getUniqueId())) {
            return false;
        }

        remove(player.getUniqueId());

        player.getInventory().clear();
        player.updateInventory();

        return true;
    }

}
