package net.pooleaf.core.modules.gui.bukkit.quickbar;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.quickbar.event.SlotClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuickBarListener implements Listener {

    private Map<UUID, Long> lastGuiClick = new HashMap<>();


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (GuiModule.getQuickBarManager().exists(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();
            GuiModule.getQuickBarManager().remove(event.getPlayer().getUniqueId());
            lastGuiClick.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        QuickBar quickBar = GuiModule.getQuickBarManager().get(event.getPlayer().getUniqueId());
        if (quickBar != null) {
            event.getItemDrop().remove();
            event.setCancelled(true);

            Bukkit.getScheduler().runTaskLater((Plugin) Core.getPlugin(), () -> {
                quickBar.updateFakeIcons();
            }, 1L);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (GuiModule.getQuickBarManager().exists(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);

            Bukkit.getScheduler().runTaskLater((Plugin) Core.getPlugin(), () -> {
                QuickBar quickBar = GuiModule.getQuickBarManager().get(e.getWhoClicked().getUniqueId());
                quickBar.updateFakeIcons();
            }, 1L);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        QuickBar quickBar = GuiModule.getQuickBarManager().get(event.getPlayer().getUniqueId());
        if (quickBar == null) return;

        UUID uuid = event.getPlayer().getUniqueId();

        // 클릭 딜레이 체크
        if (lastGuiClick.containsKey(uuid) && System.currentTimeMillis() - lastGuiClick.get(uuid) < quickBar.getClickDelayMillis()) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTaskLater((Plugin) Core.getPlugin(), () -> quickBar.updateFakeIcons(), 1L);
            return;
        }
        lastGuiClick.put(uuid, System.currentTimeMillis());

        // SlotClickEvent
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

        quickBar.updateFakeIcons();
    }


}
