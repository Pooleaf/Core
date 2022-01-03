package net.pooleaf.core.gui.inventory.event;

import lombok.Data;
import net.pooleaf.core.event.bukkit.event.HandlerEvent;
import net.pooleaf.core.gui.inventory.Gui;
import net.pooleaf.core.gui.inventory.GuiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;

@Data
public class GuiOpenEvent extends HandlerEvent {

    private final Player player;

    private final Gui gui;

    private final InventoryOpenEvent inventoryOpenEvent;

    public GuiOpenEvent(InventoryOpenEvent inventoryOpenEvent) {
        this.player = (Player) inventoryOpenEvent.getPlayer();
        this.gui = GuiManager.getPlayerGuis().get(this.player.getUniqueId());
        this.inventoryOpenEvent = inventoryOpenEvent;
    }

}
