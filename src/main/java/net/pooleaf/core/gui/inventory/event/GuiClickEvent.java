package net.pooleaf.core.gui.inventory.event;

import lombok.Data;
import net.pooleaf.core.event.bukkit.event.CancellableEvent;
import net.pooleaf.core.gui.inventory.Gui;
import net.pooleaf.core.gui.inventory.GuiClickAction;
import net.pooleaf.core.gui.inventory.GuiManager;
import net.pooleaf.core.gui.inventory.Icon;
import net.pooleaf.core.gui.inventory.Panel;
import net.pooleaf.core.gui.inventory.PositionCalculator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Data
public class GuiClickEvent extends CancellableEvent {

    private final Player player;

    private final Gui gui;

    private final int position;

    private final Object clicked;
    private final Panel clickedPanel;

    private final GuiClickAction clickAction;

    private final InventoryClickEvent inventoryClickEvent;

    public GuiClickEvent(InventoryClickEvent inventoryClickEvent) {
        this.player = (Player) inventoryClickEvent.getWhoClicked();

        this.gui = GuiManager.getPlayerGuis().get(this.player.getUniqueId());
        this.position = inventoryClickEvent.getSlot();

        Object[] clickedData = gui.getWithPanel(position);
        this.clicked = (clickedData == null) ? null : clickedData[0];
        this.clickedPanel = (clickedData == null) ? null : (Panel) clickedData[1];

        switch (inventoryClickEvent.getAction()) {
            case PICKUP_ALL:
                clickAction = GuiClickAction.LEFT_HOLD; break;
            case PLACE_ALL:
                clickAction = GuiClickAction.LEFT_PLACE; break;
            case PICKUP_HALF:
                clickAction = GuiClickAction.RIGHT_HOLD; break;
            case PLACE_ONE:
                clickAction = GuiClickAction.RIGHT_PLACE; break;
            case MOVE_TO_OTHER_INVENTORY:
                clickAction = GuiClickAction.SHIFT_CLICK; break;
            case COLLECT_TO_CURSOR:
                clickAction = GuiClickAction.DOUBLE_CLICK; break;
            case CLONE_STACK:
                clickAction = GuiClickAction.WHEEL_CLICK; break;
            case DROP_ONE_SLOT:
                clickAction = GuiClickAction.DROP; break;
            case NOTHING:
                clickAction = GuiClickAction.NOTHING; break;
            default:
                clickAction = GuiClickAction.OTHER; break;
        }

        this.inventoryClickEvent = inventoryClickEvent;

        setCancelled(true);
    }


    public boolean isIcon() {
        return clicked != null && clicked instanceof Icon;
    }

    public int getX() {
        return PositionCalculator.getX(position, 9);
    }

    public int getY() {
        return PositionCalculator.getY(position, 9);
    }

    public ItemStack getItem() {
        return (ItemStack) clicked;
    }

    public Icon getIcon() {
        return isIcon() ? (Icon) clicked : null;
    }

}
