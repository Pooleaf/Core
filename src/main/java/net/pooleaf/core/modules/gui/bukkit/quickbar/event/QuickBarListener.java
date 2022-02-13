package net.pooleaf.core.modules.gui.bukkit.quickbar.event;

import net.pooleaf.core.modules.gui.bukkit.quickbar.QuickBar;
import net.pooleaf.core.modules.gui.bukkit.quickbar.QuickBarManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuickBarListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (QuickBarManager.getPlayerQuickBars().containsKey(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();
            QuickBarManager.getPlayerQuickBars().remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        QuickBar quickBar = QuickBarManager.getPlayerQuickBars().get(event.getPlayer().getUniqueId());
        if (quickBar != null) {
            quickBar.setTo(event.getPlayer());
            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (QuickBarManager.getPlayerQuickBars().containsKey(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        QuickBar quickBar = QuickBarManager.getPlayerQuickBars().get(event.getPlayer().getUniqueId());
        if (quickBar == null) return;

        SlotClickEvent slotClickEvent = new SlotClickEvent(event);
        if (slotClickEvent.getClicked() == null) return;

        event.setCancelled(true);

        try {
            quickBar.onClick(slotClickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            slotClickEvent.getClicked().onClick(slotClickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
