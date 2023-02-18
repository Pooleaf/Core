package net.pooleaf.core.modules.gui.bukkit.inventory;

import net.pooleaf.core.BukkitCoreBootstrapPlugin;
import net.pooleaf.core.modules.gui.GuiModule;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.*;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryGuiListener implements Listener {

    private Map<UUID, Long> lastGuiClick = new HashMap<>();


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // 퇴장 시 Gui 제거
        if (GuiModule.getInventoryGuiManager().exists(event.getPlayer().getUniqueId())) {
            event.getPlayer().closeInventory();
        }

        lastGuiClick.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventoryGui gui = GuiModule.getInventoryGuiManager().get(event.getPlayer().getUniqueId());
        if (gui == null) return;

        // GuiOpenEvent
        InventoryGuiOpenEvent guiOpenEvent = new InventoryGuiOpenEvent(event);

        // InventoryGui.onOpen
        try {
            gui.onOpen(guiOpenEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bukkit Event
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

        // InventoryGui.onClose
        try {
            gui.onClose(guiCloseEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bukkit Event
        try {
            Bukkit.getPluginManager().callEvent(guiCloseEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cancel 다시 열기
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

        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        // Gui 범위 밖 클릭 캔슬
        if (event.getClickedInventory() == null) {
            event.setCancelled(true);

            if (event.getWhoClicked().isOp()) {
                if (lastGuiClick.containsKey(uuid) && System.currentTimeMillis() - lastGuiClick.get(uuid) < 500L) {
                    gui.updateAsynchronously();
                    Messager.sendMessage(event.getWhoClicked(), "GUI를 강제로 새로고침했습니다.");
                    return;
                }

                lastGuiClick.put(uuid, System.currentTimeMillis());
            }
            return;
        }

        // 플레이어 인벤토리를 클릭했을 경우
        if (event.getClickedInventory().equals(player.getInventory())) {
            InventoryGuiPlayerInventoryClickEvent inventoryGuiPlayerInventoryClickEvent = new InventoryGuiPlayerInventoryClickEvent(event);

            // InventoryGui.onPlayerInventoryClick
            try {
                gui.onPlayerInventoryClick(inventoryGuiPlayerInventoryClickEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Bukkit Event
            try {
                Bukkit.getPluginManager().callEvent(inventoryGuiPlayerInventoryClickEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Cancel
            event.setCancelled(inventoryGuiPlayerInventoryClickEvent.isCancelled());

            return;
        }

        // GuiClickEvent
        InventoryGuiClickEvent guiClickEvent = new InventoryGuiClickEvent(event);

        // FakeInventoryIcon 새로고침
        if (guiClickEvent.getClicked() instanceof FakeInventoryIcon) {
            player.updateInventory();
        }
        Bukkit.getScheduler().runTaskLater(BukkitCoreBootstrapPlugin.getInstance(), () -> gui.updateFakeIcon(player), 1L);

        // 클릭 딜레이 체크
        if (lastGuiClick.containsKey(uuid) && System.currentTimeMillis() - lastGuiClick.get(uuid) < gui.getClickDelayMillis()) {
            event.setCancelled(true);
            return;
        }
        lastGuiClick.put(uuid, System.currentTimeMillis());

        // InventoryGui.onClick
        try {
            gui.onClick(guiClickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Gui Bukkit Event
        try {
            Bukkit.getPluginManager().callEvent(guiClickEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Icon Bukkit Event
        if (guiClickEvent.isIcon()) {
            try {
                guiClickEvent.getIcon().onClick(guiClickEvent);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        // Cancel
        event.setCancelled(guiClickEvent.isCancelled());
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryGui gui = GuiModule.getInventoryGuiManager().get(event.getWhoClicked().getUniqueId());
        if (gui == null) return;

        // GuiDragEvent
        InventoryGuiDragEvent guiDragEvent = new InventoryGuiDragEvent(event);

        // InventoryGui.onDrag
        try {
            gui.onDrag(guiDragEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Gui Bukkit Event
        try {
            Bukkit.getPluginManager().callEvent(guiDragEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cancel
        event.setCancelled(guiDragEvent.isCancelled());
    }

}
