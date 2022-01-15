package net.pooleaf.core.gui.inventory;

import com.google.common.base.Preconditions;
import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.gui.inventory.event.GuiClickEvent;
import net.pooleaf.core.gui.inventory.event.GuiCloseEvent;
import net.pooleaf.core.gui.inventory.event.GuiDragEvent;
import net.pooleaf.core.gui.inventory.event.GuiOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class Gui {

    private static final String MAIN_PANEL = "mainPanel";


    private String title;
    private int row;

    private Inventory inventory;

    private Map<Integer, Panel> panels = new HashMap<>();


    public Gui(String title, int row) {
        this.title = title;
        this.row = row;

        inventory = Bukkit.createInventory(null, 9 * row, title);

        panels.put(0, new Panel(MAIN_PANEL, 9, row));
    }

    public Panel createPanel(String name, int x, int y, int width, int height) {
        Panel newPanel = new Panel(name, width, height);
        panels.put(PositionCalculator.calculatePosition(x, y), newPanel);

        return newPanel;
    }

    public Panel getMainPanel() {
        return panels.get(0);
    }

    public Panel getPanel(String name) {
        for (Panel panel : panels.values()) {
            if (panel.getName().equals(name)) return panel;
        }

        return null;
    }

    public Object get(int x, int y) {
        Preconditions.checkArgument(1 <= x && x <= 9 && 1 <= y && y <= row, "아이템 위치가 Gui 범위를 벗어났습니다. (x: %s, y: %s, row: %s)", x, y, row);

        for (Map.Entry<Integer, Panel> entry : panels.entrySet()) {
            int panelPosition = entry.getKey();
            Panel panel = entry.getValue();

            int panelX = PositionCalculator.getX(panelPosition, 9);
            int panelY = PositionCalculator.getY(panelPosition, 9);

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
    public Object[] getWithPanel(int x, int y) {
        Preconditions.checkArgument(1 <= x && x <= 9 && 1 <= y && y <= row, "아이템 위치가 Gui 범위를 벗어났습니다. (x: %s, y: %s, row: %s)", x, y, row);

        for (Map.Entry<Integer, Panel> entry : panels.entrySet()) {
            int panelPosition = entry.getKey();
            Panel panel = entry.getValue();

            int panelX = PositionCalculator.getX(panelPosition, 9);
            int panelY = PositionCalculator.getY(panelPosition, 9);

            int itemX = x - (panelX - 1);
            int itemY = y - (panelY - 1);

            Object item = panel.get(itemX, itemY);
            if (item != null) return new Object[] { item, panel };
        }

        return null;
    }

    public Object get(int position) {
        int x = PositionCalculator.getX(position, 9);
        int y = PositionCalculator.getY(position, 9);

        return get(x, y);
    }

    public Object[] getWithPanel(int position) {
        int x = PositionCalculator.getX(position, 9);
        int y = PositionCalculator.getY(position, 9);

        return getWithPanel(x, y);
    }

    public ItemStack getItem(int position) {
        return (ItemStack) get(position);
    }

    public ItemStack getItem(int x, int y) {
        return getItem(PositionCalculator.calculatePosition(x, y));
    }

    public Icon getIcon(int position) {
        return (Icon) get(position);
    }

    public Icon getIcon(int x, int y) {
        return getIcon(PositionCalculator.calculatePosition(x, y));
    }

    public List<Icon> getIcons() {
        List<Icon> icons = new ArrayList<>();

        panels.values().forEach(panel -> icons.addAll(
                panel.getItems().values().stream()
                        .filter(item -> item instanceof Icon)
                        .map(item -> (Icon) item)
                        .collect(Collectors.toList())
        ));

        return icons;
    }

    /**
     * 이 GUI를 보고 있는 플레이어 목록을 반환합니다.
     * @return 이 GUI를 보고 있는 플레이어 목록
     */
    public List<Player> getWatchers() {
        List<Player> watchers = new ArrayList<>();

        for (Map.Entry<UUID, Gui> entry : GuiManager.getPlayerGuis().entrySet()) {
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

    public void update() {
        onUpdate();

        panels.forEach((panelPosition, panel) -> {
            int panelX = PositionCalculator.getX(panelPosition, 9);
            int panelY = PositionCalculator.getY(panelPosition, 9);

            panel.getItems().forEach((itemPosition, item) -> {
                int offsetX = PositionCalculator.getX(itemPosition, panel.getWidth());
                int offsetY = PositionCalculator.getY(itemPosition, panel.getWidth());

                int realX = panelX + offsetX - 1;
                int realY = panelY + offsetY - 1;

                int realPosition = PositionCalculator.calculatePosition(realX, realY);

                if (item instanceof ItemStack) {
                    inventory.setItem(realPosition, (ItemStack) item);
                } else if (item instanceof Icon) {
                    ((Icon) item).update();
                    inventory.setItem(realPosition, ((Icon) item).getItem());
                }
            });
        });
    }

    public void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

    public void onOpen(GuiOpenEvent event) {}

    public void onClose(GuiCloseEvent event) {}

    public void onClick(GuiClickEvent event) {}

    public void onDrag(GuiDragEvent event) {}

    public void open(Player player) {
        player.closeInventory();

        GuiManager.getPlayerGuis().put(player.getUniqueId(), this);
        player.openInventory(inventory);
    }

    public void closeAll() {
        getWatchers().forEach(player -> player.closeInventory());
    }

}
