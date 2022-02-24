package net.pooleaf.core.modules.gui.bukkit.quickbar;

import net.pooleaf.core.modules.support.common.manager.AbstractManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QuickBarManager extends AbstractManager<UUID, QuickBar> {

    public void setTo(Player player, QuickBar quickBar) {
        set(player.getUniqueId(), quickBar);

        player.getInventory().clear();
        quickBar.getSlots().forEach((position, slot) -> player.getInventory().setItem(position, slot.getItem()));

        player.updateInventory();
    }

    public boolean removeTo(Player player) {
        if (!exists(player.getUniqueId())) {
            return false;
        }

        player.getInventory().clear();
        player.updateInventory();
        return true;
    }

}
