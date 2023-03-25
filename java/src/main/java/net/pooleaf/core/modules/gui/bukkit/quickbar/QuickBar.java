package net.pooleaf.core.modules.gui.bukkit.quickbar;

import com.google.common.base.Objects;
import net.pooleaf.core.modules.gui.bukkit.quickbar.event.SlotClickEvent;
import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.gui.GuiModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class QuickBar {

    private Map<Integer, Slot> slots = new HashMap<>();

    private long clickDelayMillis = 200L;


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

    /**
     * 이 퀵바를 보고 있는 플레이어 목록을 반환합니다.
     * @return 퀵바를 보고 있는 플레이어 목록
     */
    public List<Player> getWatchers() {
        return GuiModule.getQuickBarManager().getDatas().entrySet().stream()
                .filter(entry -> entry.getValue().equals(this))
                .map(entry -> Bukkit.getPlayer(entry.getKey()))
                .collect(Collectors.toList());
    }

    public void onUpdate() {}

    public void update() {
        onUpdate();

        slots.values().forEach(slot -> slot.update());
        getWatchers().forEach(watcher -> {
            slots.forEach((x, slot) -> watcher.getInventory().setItem(x - 1, slot.getItem()));

            watcher.updateInventory();
        });

        updateFakeIcons();
    }

    public void updateFakeIcons() {
        List<Player> watchers = getWatchers();

        for (int i = 1; i <= 9; i++) {
            Slot slot = slots.get(i);

            if (slot == null) {
                continue;
            }

            if (slot instanceof FakeSlot) {
                int itemPosition = i;
                watchers.forEach(watchPlayer -> ((FakeSlot) slot).update(watchPlayer, itemPosition - 1));
            }
        }
    }

    public void updateAsynchronously() {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) Core.getPlugin(), () -> update());
    }

    public void onClick(SlotClickEvent event) {}

    public void setTo(Player player) {
        GuiModule.getQuickBarManager().setTo(player, this);
    }

    public void removeTo(Player player) {
        QuickBar quickBar = GuiModule.getQuickBarManager().get(player.getUniqueId());
        if (!Objects.equal(this, quickBar)) { // 다른 퀵바를 쓰고 있는 플레이어면 제거 안함
            return;
        }

        GuiModule.getQuickBarManager().removeTo(player);
    }

}
