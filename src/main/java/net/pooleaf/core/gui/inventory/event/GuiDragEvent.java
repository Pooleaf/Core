package net.pooleaf.core.gui.inventory.event;

import lombok.Data;
import net.pooleaf.core.event.bukkit.event.CancellableEvent;
import net.pooleaf.core.gui.inventory.Gui;
import net.pooleaf.core.gui.inventory.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;

@Data
public class GuiDragEvent extends CancellableEvent {

    private final Player player;

    private final Gui gui;

    private final InventoryDragEvent inventoryDragEvent;

    public GuiDragEvent(InventoryDragEvent inventoryDragEvent) {
        this.player = (Player) inventoryDragEvent.getWhoClicked();
        this.gui = GuiManager.getPlayerGuis().get(this.player.getUniqueId());
        this.inventoryDragEvent = inventoryDragEvent;

        setCancelled(true);
    }

}
