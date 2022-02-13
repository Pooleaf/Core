package net.pooleaf.core.modules.gui.bukkit.inventory.event;

import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.InventoryGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryGuiListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // 퇴장 시 Gui 제거
        if (GuiModule.getInventoryGuiManager().exists(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryGui gui = GuiModule.getInventoryGuiManager().get(event.getPlayer().getUniqueId());
        if (gui == null) return;

        // GuiOpenEvent
        InventoryGuiOpenEvent guiOpenEvent = new InventoryGuiOpenEvent(event);
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
        InventoryGui gui = GuiModule.getInventoryGuiManager().get(event.getPlayer().getUniqueId());
        if (gui == null) return;

        // GuiCloseEvent
        InventoryGuiCloseEvent guiCloseEvent = new InventoryGuiCloseEvent(event);
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
        GuiModule.getInventoryGuiManager().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryGui gui = GuiModule.getInventoryGuiManager().get(event.getWhoClicked().getUniqueId());
        if (gui == null) return;

        // Gui 범위 밖 클릭 캔슬
        if (event.getSlot() < 0) {
            event.setCancelled(true);
            return;
        }

        // GuiClickEvent
        InevntoryGuiClickEvent guiClickEvent = new InevntoryGuiClickEvent(event);
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
        InventoryGui gui = GuiModule.getInventoryGuiManager().get(event.getWhoClicked().getUniqueId());
        if (gui == null) return;

        // GuiDragEvent
        InventoryGuiDragEvent guiDragEvent = new InventoryGuiDragEvent(event);
        try {
            Bukkit.getPluginManager().callEvent(guiDragEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        event.setCancelled(guiDragEvent.isCancelled());
    }

}
