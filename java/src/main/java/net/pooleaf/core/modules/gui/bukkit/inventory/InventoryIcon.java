package net.pooleaf.core.modules.gui.bukkit.inventory;

import net.pooleaf.core.modules.gui.bukkit.inventory.events.InventoryGuiClickEvent;
import lombok.Data;
import net.pooleaf.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@Data
public abstract class InventoryIcon {

    private ItemStack item;


    protected abstract ItemStack updateItem();

    public void update() {
        item = updateItem();
    }

    public void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

    public void onClick(InventoryGuiClickEvent event) {}

}
