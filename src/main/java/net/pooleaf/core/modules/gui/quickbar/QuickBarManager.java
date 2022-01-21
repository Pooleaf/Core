package net.pooleaf.core.modules.gui.quickbar;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuickBarManager {

    @Getter
    private static Map<UUID, QuickBar> playerQuickBars = new HashMap<>();


    public static void setTo(Player player, QuickBar quickBar) {
        playerQuickBars.put(player.getUniqueId(), quickBar);

        player.getInventory().clear();
        quickBar.getSlots().forEach((position, slot) -> player.getInventory().setItem(position, slot.getItem()));

        player.updateInventory();
    }

    public static boolean removeTo(Player player) {
        if (playerQuickBars.remove(player) == null) return false;

        player.getInventory().clear();
        player.updateInventory();
        return true;
    }

}
