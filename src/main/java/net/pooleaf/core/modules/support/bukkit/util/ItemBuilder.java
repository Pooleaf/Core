package net.pooleaf.core.modules.support.bukkit.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder() {
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(int typeId) {
        item = ItemUtil.getItemStack(typeId);
    }

    public ItemBuilder(int typeId, short durability) {
        item = ItemUtil.getItemStack(typeId);
        item.setDurability(durability);
    }

    public ItemBuilder(Material type) {
        item = new ItemStack(type);
    }

    public ItemBuilder(String itemCode) {
        item = ItemUtil.getItemStack(itemCode);
    }

    public ItemBuilder durability(short durability) {
        item.setDurability(durability);

        return this;
    }

    public ItemBuilder amount(int amount) {
        if (amount < 1) {
            item.setAmount(1);
        } else if (amount > 128) {
            item.setAmount(128);
        } else {
            item.setAmount(amount);
        }

        return this;
    }

    public ItemBuilder displayName(String displayName) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(String... lore) {
        ItemMeta itemMeta = item.getItemMeta();

        List<String> newLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        for (String line : lore) {
            newLore.add(line);
        }
        itemMeta.setLore(newLore);

        item.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();

        List<String> newLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        newLore.addAll(lore);
        itemMeta.setLore(newLore);

        item.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(new ArrayList<>());
        item.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        item.addEnchantment(enchantment, level);

        return this;
    }

    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
        item.addEnchantments(enchantments);

        return this;
    }

    public ItemBuilder skull(String playerName) {
        item.setType(ItemUtil.getMaterial(397));
        item.setDurability((short) 3);

        ItemMeta meta = item.getItemMeta();
        ((SkullMeta) meta).setOwner(playerName);
        item.setItemMeta(meta);

        return this;
    }

    public ItemStack build() {
        return item;
    }

}