package net.pooleaf.core.modules.support.bukkit.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtil {

    public static int getInventorySpace(Inventory inventory, ItemStack item) {
        int space = 0;

        if (inventory instanceof PlayerInventory) {
            for (ItemStack inventoryItem : inventory.getContents()) {
                if (inventoryItem == null || inventoryItem.getType() == Material.AIR) {
                    space += 64;
                } else {
                    space += inventoryItem.getMaxStackSize() - inventoryItem.getAmount();
                }
            }
        } else {
            for (ItemStack inventoryItem : inventory) {
                if (inventoryItem == null || inventoryItem.getType() == Material.AIR) {
                    space += 64;
                } else {
                    space += inventoryItem.getMaxStackSize() - inventoryItem.getAmount();
                }
            }
        }

        return space;
    }

    public static boolean hasInventorySpace(Inventory inventory, ItemStack addItem) {
        return getInventorySpace(inventory, addItem) >= addItem.getAmount();
    }

    public static int getEmptySlotAmount(Inventory inventory) {
        int amount = 0;

        for (ItemStack item : inventory) {
            if (item == null || item.getType() == Material.AIR) {
                amount++;
            }
        }

        return amount;
    }

    public static int getItemAmount(Inventory inventory, ItemStack item) {
        int amount = 0;

        for (ItemStack inventoryItem : inventory) {
            if (inventoryItem != null && inventoryItem.isSimilar(item)) {
                amount += inventoryItem.getAmount();
            }
        }

        return amount;
    }

    public static int takeItem(Inventory inventory, ItemStack item, int amount) {
        int take = 0;

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack inventoryItem = inventory.getItem(i);
            if (inventoryItem != null && inventoryItem.isSimilar(item)) {
                if (amount - take >= inventoryItem.getAmount()) {
                    inventory.setItem(i, null);
                    take += inventoryItem.getAmount();
                } else {
                    inventoryItem.setAmount(inventoryItem.getAmount() - (amount - take));
                    take += (amount - take);
                }
            }
        }

        return take;
    }

    public static int takeItemInHand(Player player, int amount) {
        ItemStack item = player.getItemInHand();
        if (item == null) return 0;

        if (amount >= item.getAmount()) {
            player.setItemInHand(null);
            return item.getAmount();
        } else {
            item.setAmount(item.getAmount() - amount);
            return amount;
        }
    }

}
