package net.pooleaf.core.modules.gui.bukkit.quickbar;

import net.pooleaf.core.modules.gui.bukkit.quickbar.event.SlotClickEvent;
import lombok.Data;
import net.pooleaf.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@Data
public abstract class Slot {

    private ItemStack item;


    protected abstract ItemStack updateItem();

    public void update() {
        item = updateItem();
    }

    public void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

    public void onClick(SlotClickEvent event) {}

}
