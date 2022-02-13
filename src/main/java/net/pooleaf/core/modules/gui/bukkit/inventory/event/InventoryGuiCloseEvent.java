package net.pooleaf.core.modules.gui.bukkit.inventory.event;

import lombok.Data;
import net.pooleaf.core.modules.eventsupport.bukkit.event.CancellableEvent;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

@Data
public class InventoryGuiCloseEvent extends CancellableEvent {

    private final Player player;

    private final InventoryGui gui;

    private final InventoryCloseEvent inventoryCloseEvent;


    public InventoryGuiCloseEvent(InventoryCloseEvent inventoryCloseEvent) {
        this.player = (Player) inventoryCloseEvent.getPlayer();
        this.gui = GuiModule.getInventoryGuiManager().get(this.player.getUniqueId());
        this.inventoryCloseEvent = inventoryCloseEvent;
    }

}
