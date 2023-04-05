package net.pooleaf.core.modules.gui.bukkit.inventory;

import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.pooleaf.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class InventoryPanel {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final InventoryGui gui;

    private final String name;

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private Map<Integer, Object> items = new HashMap<>();


    private void checkRange(int position) {
        int x = InventoryPositionCalculator.getX(position, width);
        int y = InventoryPositionCalculator.getY(position, width);

        Preconditions.checkArgument(1 <= x || x <= width || 1 <= y || y <= height, "아이템 위치가 Panel 범위를 벗어났습니다. (position: %s, x: %s, y: %s, width: %s, height: %s)", position, x, y, width, height);
    }

    public boolean hasEmptySlot() {
        int maxPosition = width * height;
        return items.size() < maxPosition;
    }

    public boolean add(Object item) {
        int maxPosition = width * height;
        for (int i = 0; i < maxPosition; i++) {
            if (!items.containsKey(i)) {
                set(i, item);

                return true;
            }
        }

        return false;
    }

    public void set(int position, Object item) {
        checkRange(position);

        if (item != null) {
            items.put(position, item);
        } else {
            items.remove(position);
        }
    }

    public void set(int x, int y, Object item) {
        set(InventoryPositionCalculator.getPosition(x, y), item);
    }

    public Object get(int position) {
        checkRange(position);

        return items.get(position);
    }

    public Object get(int x, int y) {
        return get(InventoryPositionCalculator.getPosition(x, y));
    }

    public boolean remove(int position) {
        return items.remove(position) != null;
    }

    public boolean remove(int x, int y) {
        int position = InventoryPositionCalculator.getPosition(x, y);
        return remove(position);
    }

    public ItemStack getItem(int position) {
        return (ItemStack) get(position);
    }

    public ItemStack getItem(int x, int y) {
        return getItem(InventoryPositionCalculator.getPosition(x, y));
    }

    public InventoryIcon getIcon(int position) {
        return (InventoryIcon) get(position);
    }

    public InventoryIcon getIcon(int x, int y) {
        return getIcon(InventoryPositionCalculator.getPosition(x, y));
    }

    public ItemStack getItemInInventory(int position) {
        int x = InventoryPositionCalculator.getX(position, width);
        int y = InventoryPositionCalculator.getY(position, width);

        return getItemInInventory(x, y);
    }

    public ItemStack getItemInInventory(int x, int y) {
        int realX = getRealX(x);
        int realY = getRealY(y);

        return gui.getItemInInventory(realX, realY);
    }

    public ItemStack[][] getItemsInInventory() {
        ItemStack[][] items = new ItemStack[this.width][this.height];

        for (int x = this.x; x <= this.x + this.width - 1; x++) {
            for (int y = this.y; y <= this.y + this.height - 1; y++) {
                items[x][y] = gui.getItemInInventory(x, y);
            }
        }

        return items;
    }

    public List<ItemStack> getItemListInInventory() {
        List<ItemStack> items = new ArrayList<>();

        for (int x = this.x; x <= this.x + this.width - 1; x++) {
            for (int y = this.y; y <= this.y + this.height - 1; y++) {
                ItemStack item = gui.getItemInInventory(x, y);
                if (item != null) {
                    items.add(item);
                }
            }
        }

        return items;
    }

    public int getRealX(int x) {
        return this.x + x - 1;
    }

    public int getRealY(int y) {
        return this.y + y - 1;
    }

    protected void onUpdate() {}

    public void update() {
        onUpdate();

        items.values().stream()
                .filter(item -> item instanceof InventoryIcon)
                .forEach(icon -> ((InventoryIcon) icon).update());
    }

    public void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

}
