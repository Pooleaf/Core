package net.pooleaf.core.gui.inventory.event;

import lombok.Data;
import net.pooleaf.core.event.bukkit.event.CancellableEvent;
import net.pooleaf.core.gui.inventory.Gui;
import net.pooleaf.core.gui.inventory.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

@Data
public class GuiCloseEvent extends CancellableEvent {

    private final Player player;

    private final Gui gui;

    private final InventoryCloseEvent inventoryCloseEvent;

    public GuiCloseEvent(InventoryCloseEvent inventoryCloseEvent) {
        this.player = (Player) inventoryCloseEvent.getPlayer();
        this.gui = GuiManager.getPlayerGuis().get(this.player.getUniqueId());
        this.inventoryCloseEvent = inventoryCloseEvent;
    }

}
