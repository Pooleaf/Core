package net.pooleaf.core.gui.inventory.event;

import net.pooleaf.core.gui.inventory.Gui;
import net.pooleaf.core.gui.inventory.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GuiListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // 퇴장 시 Gui 제거
        if (GuiManager.getPlayerGuis().containsKey(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Gui gui = GuiManager.getPlayerGuis().get(event.getPlayer().getUniqueId());
        if (gui == null) return;

        // GuiOpenEvent
        GuiOpenEvent guiOpenEvent = new GuiOpenEvent(event);
        try {
            gui.onOpen(guiOpenEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bukkit.getPluginManager().callEvent(guiOpenEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Gui gui = GuiManager.getPlayerGuis().get(event.getPlayer().getUniqueId());
        if (gui == null) return;

        // GuiCloseEvent
        GuiCloseEvent guiCloseEvent = new GuiCloseEvent(event);
        try {
            gui.onClose(guiCloseEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bukkit.getPluginManager().callEvent(guiCloseEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (guiCloseEvent.isCancelled()) {
            gui.open((Player) event.getPlayer());
        }

        // Gui 제거
        GuiManager.getPlayerGuis().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Gui gui = GuiManager.getPlayerGuis().get(event.getWhoClicked().getUniqueId());
        if (gui == null) return;

        // Gui 범위 밖 클릭 캔슬
        if (event.getRawSlot() < 0) {
            event.setCancelled(true);
        }

        // GuiClickEvent
        GuiClickEvent guiClickEvent = new GuiClickEvent(event);
        try {
            gui.onClick(guiClickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bukkit.getPluginManager().callEvent(guiClickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (guiClickEvent.isIcon()) {
            try {
                guiClickEvent.getIcon().onClick(guiClickEvent);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        event.setCancelled(guiClickEvent.isCancelled());
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Gui gui = GuiManager.getPlayerGuis().get(event.getWhoClicked().getUniqueId());
        if (gui == null) return;

        // GuiDragEvent
        GuiDragEvent guiDragEvent = new GuiDragEvent(event);
        try {
            Bukkit.getPluginManager().callEvent(guiDragEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        event.setCancelled(guiDragEvent.isCancelled());
    }

}
