package net.pooleaf.core.modules.gui.bukkit.inventory.events;

import lombok.Data;
import net.pooleaf.core.modules.eventsupport.bukkit.events.HandlerEvent;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

@Data
public class InventoryGuiOpenEvent extends HandlerEvent {

    private final Player player;

    private final InventoryGui gui;

    private final InventoryOpenEvent inventoryOpenEvent;

    public InventoryGuiOpenEvent(InventoryOpenEvent inventoryOpenEvent) {
        this.player = (Player) inventoryOpenEvent.getPlayer();
        this.gui = GuiModule.getInventoryGuiManager().get(this.player.getUniqueId());
        this.inventoryOpenEvent = inventoryOpenEvent;
    }

}
