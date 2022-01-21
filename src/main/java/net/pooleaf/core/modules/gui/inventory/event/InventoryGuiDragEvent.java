package net.pooleaf.core.modules.gui.inventory.event;

import lombok.Data;
import net.pooleaf.core.modules.event.bukkit.event.CancellableEvent;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.inventory.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;

@Data
public class InventoryGuiDragEvent extends CancellableEvent {

    private final Player player;

    private final InventoryGui gui;

    private final InventoryDragEvent inventoryDragEvent;


    public InventoryGuiDragEvent(InventoryDragEvent inventoryDragEvent) {
        this.player = (Player) inventoryDragEvent.getWhoClicked();
        this.gui = GuiModule.getInventoryGuiManager().get(this.player.getUniqueId());
        this.inventoryDragEvent = inventoryDragEvent;

        setCancelled(true);
    }

}
