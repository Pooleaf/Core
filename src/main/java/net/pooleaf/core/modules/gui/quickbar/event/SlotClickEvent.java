package net.pooleaf.core.modules.gui.quickbar.event;

import lombok.Data;
import net.pooleaf.core.modules.event.bukkit.event.CancellableEvent;
import net.pooleaf.core.modules.gui.quickbar.QuickBar;
import net.pooleaf.core.modules.gui.quickbar.QuickBarManager;
import net.pooleaf.core.modules.gui.quickbar.Slot;
import net.pooleaf.core.modules.gui.quickbar.SlotClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

@Data
public class SlotClickEvent extends CancellableEvent {

    private final Player player;

    private final QuickBar quickBar;
    private final int position;
    private final Slot clicked;

    private SlotClickAction slotClickAction;

    private final PlayerInteractEvent playerInteractEvent;


    public SlotClickEvent(PlayerInteractEvent playerInteractEvent) {
        this.player = playerInteractEvent.getPlayer();

        this.quickBar = QuickBarManager.getPlayerQuickBars().get(this.player.getUniqueId());
        this.position = this.player.getInventory().getHeldItemSlot();
        this.clicked = quickBar.getSlot(getX());

        switch (playerInteractEvent.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                if (this.player.isSneaking()) {
                    this.slotClickAction = SlotClickAction.SHIFT_LEFT_CLICK;
                } else {
                    this.slotClickAction = SlotClickAction.LEFT_CLICK;
                }
                break;

            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                if (this.player.isSneaking()) {
                    this.slotClickAction = SlotClickAction.SHIFT_RIGHT_CLICK;
                } else {
                    this.slotClickAction = SlotClickAction.RIGHT_CLICK;
                }
                break;
        }

        this.playerInteractEvent = playerInteractEvent;
    }

    public int getX() {
        return position + 1;
    }

}
