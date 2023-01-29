package net.pooleaf.core.modules.gui.bukkit.inventory.events;

import net.pooleaf.core.modules.eventsupport.bukkit.events.CancellableEvent;
import lombok.Data;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
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
