package net.pooleaf.core.modules.gui.bukkit.quickbar;

import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.gui.bukkit.quickbar.event.SlotClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@Data
public class QuickBar {

    private Map<Integer, Slot> slots = new HashMap<>();


    /**
     * QuickBar의 x번째 Slot을 설정합니다.
     * slot을 null로 사용할 경우 삭제합니다.
     * @param x 1~9
     * @param slot Slot, null 사용 시 삭제
     */
    public void setSlot(int x, Slot slot) {
        if (slot == null) {
            slots.remove(x);
            return;
        }

        slots.put(x, slot);
    }

    /**
     * QuickBar의 x번째 Slot을 반환합니다.
     * @param x 1~9
     * @return Slot
     */
    public Slot getSlot(int x) {
        return slots.get(x);
    }

    public void onUpdate() {}

    public void update() {
        onUpdate();

        slots.values().forEach(slot -> slot.update());
    }

    public void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

    public void onClick(SlotClickEvent event) {}

    public void setTo(Player player) {
        QuickBarManager.setTo(player, this);
    }

    public void removeTo(Player player) {
        QuickBar quickBar = QuickBarManager.getPlayerQuickBars().remove(player.getUniqueId());
        if (quickBar == null || getClass().isAssignableFrom(quickBar.getClass())) return;

        player.getInventory().clear();
        player.updateInventory();
    }

}
