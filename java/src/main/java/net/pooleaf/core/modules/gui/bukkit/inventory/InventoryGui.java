package net.pooleaf.core.modules.gui.bukkit.inventory;

import com.google.common.base.Preconditions;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.InevntoryGuiClickEvent;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.InventoryGuiCloseEvent;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.InventoryGuiDragEvent;
import net.pooleaf.core.modules.gui.bukkit.inventory.events.InventoryGuiOpenEvent;
import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.gui.GuiModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class InventoryGui {

    private static final String MAIN_PANEL = "mainPanel";


    private String title;
    private int row;

    private Inventory inventory;

    private Map<Integer, InventoryPanel> panels = new HashMap<>();

    private long clickDelayMillis = 200L;


    public InventoryGui(String title, int row) {
        this.title = title;
        this.row = row;

        inventory = Bukkit.createInventory(null, 9 * row, title);

        panels.put(0, new InventoryPanel(MAIN_PANEL, 9, row));
    }

    public final InventoryPanel createPanel(String name, int x, int y, int width, int height) {
        InventoryPanel newPanel = new InventoryPanel(name, width, height);
        panels.put(InventoryPositionCalculator.calculatePosition(x, y), newPanel);

        return newPanel;
    }

    public final InventoryPanel getMainPanel() {
        return panels.get(0);
    }

    public final InventoryPanel getPanel(String name) {
        for (InventoryPanel panel : panels.values()) {
            if (panel.getName().equals(name)) return panel;
        }

        return null;
    }

    public final Object get(int x, int y) {
        Preconditions.checkArgument(1 <= x && x <= 9 && 1 <= y && y <= row, "아이템 위치가 Gui 범위를 벗어났습니다. (x: %s, y: %s, row: %s)", x, y, row);

        for (Map.Entry<Integer, InventoryPanel> entry : panels.entrySet()) {
            int panelPosition = entry.getKey();
            InventoryPanel panel = entry.getValue();

            int panelX = InventoryPositionCalculator.getX(panelPosition, 9);
            int panelY = InventoryPositionCalculator.getY(panelPosition, 9);

            int itemX = x - (panelX - 1);
            int itemY = y - (panelY - 1);

            Object item = panel.get(itemX, itemY);
            if (item != null) return item;
        }

        return null;
    }

    /**
     * Get item or icon with panel
     * @param x Item X
     * @param y Item Y
     * @return 0: Item, 1: Panel
     */
    public final Object[] getWithPanel(int x, int y) {
        Preconditions.checkArgument(1 <= x && x <= 9 && 1 <= y && y <= row, "아이템 위치가 Gui 범위를 벗어났습니다. (x: %s, y: %s, row: %s)", x, y, row);

        for (Map.Entry<Integer, InventoryPanel> entry : panels.entrySet()) {
            int panelPosition = entry.getKey();
            InventoryPanel panel = entry.getValue();

            int panelX = InventoryPositionCalculator.getX(panelPosition, 9);
            int panelY = InventoryPositionCalculator.getY(panelPosition, 9);

            int itemX = x - (panelX - 1);
            int itemY = y - (panelY - 1);

            Object item = panel.get(itemX, itemY);
            if (item != null) return new Object[] { item, panel };
        }

        return null;
    }

    public final Object get(int position) {
        int x = InventoryPositionCalculator.getX(position, 9);
        int y = InventoryPositionCalculator.getY(position, 9);

        return get(x, y);
    }

    public final Object[] getWithPanel(int position) {
        int x = InventoryPositionCalculator.getX(position, 9);
        int y = InventoryPositionCalculator.getY(position, 9);

        return getWithPanel(x, y);
    }

    public final ItemStack getItem(int position) {
        return (ItemStack) get(position);
    }

    public final ItemStack getItem(int x, int y) {
        return getItem(InventoryPositionCalculator.calculatePosition(x, y));
    }

    public final InventoryIcon getIcon(int position) {
        return (InventoryIcon) get(position);
    }

    public final InventoryIcon getIcon(int x, int y) {
        return getIcon(InventoryPositionCalculator.calculatePosition(x, y));
    }

    public final List<InventoryIcon> getIcons() {
        List<InventoryIcon> icons = new ArrayList<>();

        panels.values().forEach(panel -> icons.addAll(
                panel.getItems().values().stream()
                        .filter(item -> item instanceof InventoryIcon)
                        .map(item -> (InventoryIcon) item)
                        .collect(Collectors.toList())
        ));

        return icons;
    }

    /**
     * 이 GUI를 보고 있는 플레이어 목록을 반환합니다.
     * @return 이 GUI를 보고 있는 플레이어 목록
     */
    public final List<Player> getWatchers() {
        List<Player> watchers = new ArrayList<>();

        for (Map.Entry<UUID, InventoryGui> entry : GuiModule.getInventoryGuiManager().getDatas().entrySet()) {
            if (entry.getValue().equals(this)) {
                Player watcher = Bukkit.getPlayer(entry.getKey());
                if (watcher != null) {
                    watchers.add(watcher);
                }
            }
        }

        return watchers;
    }

    public void onUpdate() {}

    public final void update() {
        onUpdate();

        panels.forEach((panelPosition, panel) -> {
            int panelX = InventoryPositionCalculator.getX(panelPosition, 9);
            int panelY = InventoryPositionCalculator.getY(panelPosition, 9);

            panel.getItems().forEach((itemPosition, item) -> {
                int offsetX = InventoryPositionCalculator.getX(itemPosition, panel.getWidth());
                int offsetY = InventoryPositionCalculator.getY(itemPosition, panel.getWidth());

                int realX = panelX + offsetX - 1;
                int realY = panelY + offsetY - 1;

                int realPosition = InventoryPositionCalculator.calculatePosition(realX, realY);

                if (item instanceof ItemStack) {
                    inventory.setItem(realPosition, (ItemStack) item);
                } else if (item instanceof InventoryIcon) {
                    ((InventoryIcon) item).update();
                    inventory.setItem(realPosition, ((InventoryIcon) item).getItem());
                } else if (item instanceof FakeInventoryIcon) {
                    getWatchers().forEach(watchPlayer -> ((FakeInventoryIcon) item).update(watchPlayer, realPosition));
                }
            });
        });
    }

    public final void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

    public void onOpen(InventoryGuiOpenEvent event) {}

    public void onClose(InventoryGuiCloseEvent event) {}

    public void onClick(InevntoryGuiClickEvent event) {}

    public void onDrag(InventoryGuiDragEvent event) {}

    public final void open(Player player) {
        player.closeInventory();

        GuiModule.getInventoryGuiManager().set(player.getUniqueId(), this);
        player.openInventory(inventory);

        panels.forEach((panelPosition, panel) -> {
            int panelX = InventoryPositionCalculator.getX(panelPosition, 9);
            int panelY = InventoryPositionCalculator.getY(panelPosition, 9);

            panel.getItems().forEach((itemPosition, item) -> {
                if (!(item instanceof FakeInventoryIcon)) {
                    return;
                }

                int offsetX = InventoryPositionCalculator.getX(itemPosition, panel.getWidth());
                int offsetY = InventoryPositionCalculator.getY(itemPosition, panel.getWidth());

                int realX = panelX + offsetX - 1;
                int realY = panelY + offsetY - 1;

                int realPosition = InventoryPositionCalculator.calculatePosition(realX, realY);

                getWatchers().forEach(watchPlayer -> ((FakeInventoryIcon) item).update(watchPlayer, realPosition));
            });
        });
    }

    /**
     * 이 GUI를 보고 있는 모든 플레이어의 GUI를 닫습니다.
     */
    public final void closeAll() {
        Iterator<Player> iterator = getWatchers().iterator();
        while (iterator.hasNext()) {
            iterator.next().closeInventory();
            iterator.remove();
        }
    }

}
