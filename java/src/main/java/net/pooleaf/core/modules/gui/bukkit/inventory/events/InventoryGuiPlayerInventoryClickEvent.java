package net.pooleaf.core.modules.gui.bukkit.inventory.events;

import lombok.Data;
import net.pooleaf.core.modules.eventsupport.bukkit.events.CancellableEvent;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGuiClickAction;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryPositionCalculator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Data
public class InventoryGuiPlayerInventoryClickEvent extends CancellableEvent {

    private final Player player;

    private final InventoryGui gui;

    private final int position;

    private final ItemStack clicked;
    private final InventoryGuiClickAction clickAction;
    private final InventoryClickEvent inventoryClickEvent;


    public InventoryGuiPlayerInventoryClickEvent(InventoryClickEvent inventoryClickEvent) {
        this.player = (Player) inventoryClickEvent.getWhoClicked();

        this.gui = GuiModule.getInventoryGuiManager().get(this.player.getUniqueId());
        this.position = inventoryClickEvent.getSlot();

        this.clicked = inventoryClickEvent.getCurrentItem();

        switch (inventoryClickEvent.getAction()) {
            case PICKUP_ALL:
                clickAction = InventoryGuiClickAction.LEFT_HOLD; break;
            case PLACE_ALL:
                clickAction = InventoryGuiClickAction.LEFT_PLACE; break;
            case PICKUP_HALF:
                clickAction = InventoryGuiClickAction.RIGHT_HOLD; break;
            case PLACE_ONE:
                clickAction = InventoryGuiClickAction.RIGHT_PLACE; break;
            case MOVE_TO_OTHER_INVENTORY:
                clickAction = InventoryGuiClickAction.SHIFT_CLICK; break;
            case COLLECT_TO_CURSOR:
                clickAction = InventoryGuiClickAction.DOUBLE_CLICK; break;
            case CLONE_STACK:
                clickAction = InventoryGuiClickAction.WHEEL_CLICK; break;
            case DROP_ONE_SLOT:
                clickAction = InventoryGuiClickAction.DROP; break;
            case NOTHING:
                clickAction = InventoryGuiClickAction.NOTHING; break;
            default:
                clickAction = InventoryGuiClickAction.OTHER; break;
        }

        this.inventoryClickEvent = inventoryClickEvent;

        setCancelled(true);
    }

    public int getX() {
        return InventoryPositionCalculator.getX(position, 9);
    }

    public int getY() {
        return InventoryPositionCalculator.getY(position, 9);
    }

}
