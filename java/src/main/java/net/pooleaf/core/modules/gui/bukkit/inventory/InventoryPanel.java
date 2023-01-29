package net.pooleaf.core.modules.gui.bukkit.inventory;

import com.google.common.base.Preconditions;
import lombok.Data;
import net.pooleaf.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@Data
public class InventoryPanel {

    private final String name;

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

        items.put(position, item);
    }

    public void set(int x, int y, Object item) {
        set(InventoryPositionCalculator.calculatePosition(x, y), item);
    }

    public Object get(int position) {
        checkRange(position);

        return items.get(position);
    }

    public Object get(int x, int y) {
        return get(InventoryPositionCalculator.calculatePosition(x, y));
    }

    public ItemStack getItem(int position) {
        return (ItemStack) get(position);
    }

    public ItemStack getItem(int x, int y) {
        return getItem(InventoryPositionCalculator.calculatePosition(x, y));
    }

    public InventoryIcon getIcon(int position) {
        return (InventoryIcon) get(position);
    }

    public InventoryIcon getIcon(int x, int y) {
        return getIcon(InventoryPositionCalculator.calculatePosition(x, y));
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
